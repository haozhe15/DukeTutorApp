from django.shortcuts import render
from django.db import transaction
from django.db import IntegrityError
from django.db.models import Q
from django.contrib.postgres.search import SearchVector
from rest_framework import views, viewsets, generics, mixins, exceptions
from rest_framework.permissions import IsAuthenticated, BasePermission
from rest_framework.response import Response


from .models import Session, Application, Message
from .serializers import SessionSerializer, ApplicationSerializer, MessageSerializer, SearchSerializer


# Create your views here.

class TutorSessionViewSet(viewsets.ModelViewSet):
    permission_classes = (IsAuthenticated, )
    serializer_class = SessionSerializer

    def get_queryset(self):
        queryset = Session.objects.all()
        user = self.request.user
        if not user.is_staff and self.action != 'retrieve':
            # non-staff cannot update, delete or list
            # sessions not owned by himself
            queryset = queryset.filter(tutor=user)
        return queryset

    def perform_create(self, serializer):
        serializer.save(tutor=self.request.user, is_open=True)


class ApplicationPermission(BasePermission):
    def has_object_permission(self, request, view, obj):
        action = view.action
        user = request.user
        if action in ('retrieve', 'destroy'):
            return obj.applicant == user
        if action in ('update', ):
            tutor = obj.session.tutor
            return tutor == user


class ApplicationViewSet(viewsets.ModelViewSet):
    permission_classes = (IsAuthenticated, ApplicationPermission)
    serializer_class = ApplicationSerializer

    def get_queryset(self):
        queryset = Application.objects.all()
        action = self.action
        user = self.request.user
        if action == 'list':
            queryset = queryset.filter(applicant=user)
        return queryset

    @transaction.atomic
    def perform_create(self, serializer):
        user = self.request.user
        session = serializer.validated_data['session']
        if Application.objects.filter(
                session=session,
                applicant=user).exists():
            raise exceptions.ValidationError(
                    "duplicate application")
        application = serializer.save(applicant=user)
        if application.accepted != None:
            raise exceptions.ValidationError(
                    "'accepted' must be unset on creation")
        message = Message(
                sender=user,
                recipient=application.session.tutor,
                application=application)
        # TODO what to put in the message?
        message.message = "new application"
        message.save()

    @transaction.atomic
    def perform_update(self, serializer):
        # Note: only the owner can access this view
        application = serializer.instance
        accepted = serializer.validated_data['accepted']
        if application.accepted not in (None, accepted):
            raise exceptions.ValidationError(
                    "cannot change the accepted state")
        if accepted is None:
            # this is a no-op
            return
        session = Session.objects\
                .filter(pk=application.session.pk)\
                .select_for_update()\
                .get()
        if not session.is_open:
            raise exceptions.ValidationError(
                    "cannot accepted a closed session")
        session.is_open = False
        session.save()
        serializer.save()
        message = Message(
                sender=self.request.user,
                recipient=application.applicant,
                application=application)
        message.message = accepted and \
                "your application is accepted" or \
                "your application is declined"
        message.save()

    @transaction.atomic()
    def perform_destroy(self, instance):
        if instance.accepted == True:
            session = Session.objects\
                    .filter(pk=instance.session.pk)\
                    .get()
            session.is_open = True
        instance.delete()

class MessageViewSet(viewsets.ReadOnlyModelViewSet):
    permission_classes = (IsAuthenticated, )
    serializer_class = MessageSerializer

    def get_queryset(self):
        user = self.request.user
        query = Q(sender=user) | Q(recipient=user)
        queryset = Message.objects.filter(query).order_by('-timestamp')
        return queryset


class ListResponseMixin:
    response_serializer_class = None

    def get_response_serializer(self, *args, **kwargs):
        klass = self.response_serializer_class
        kwargs['context'] = self.get_serializer_context()
        return klass(*args, **kwargs)

    def list(self, request, *args, **kwargs):
        queryset = self.filter_queryset(self.get_queryset())

        page = self.paginate_queryset(queryset)
        if page is not None:
            serializer = self.get_response_serializer(page, many=True)
            return self.get_paginated_response(serializer.data)

        serializer = self.get_response_serializer(queryset, many=True)
        return Response(serializer.data)


class SessionSearchView(ListResponseMixin, generics.GenericAPIView):
    serializer_class = SearchSerializer
    response_serializer_class = SessionSerializer

    def get_queryset(self):
        if self.queryset:
            return self.queryset
        data = self.request.data
        serializer = self.get_serializer(data=data)
        serializer.is_valid(raise_exception=True)
        self.queryset = Session.objects.filter(search_vector=data['keyword'])
        return self.queryset

    def post(self, request, *args, **kwargs):
        return self.list(request, *args, **kwargs)
