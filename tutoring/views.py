from django.shortcuts import render
from django.db import transaction
from django.db import IntegrityError
from django.db.models import Q, Max
from django.contrib.postgres.search import SearchVector
from rest_framework import views, viewsets, generics, mixins, exceptions
from rest_framework.permissions import IsAuthenticated, BasePermission
from rest_framework.response import Response


from .models import Session, Application, Message, Feedback
from .serializers import SessionSerializer, \
        ApplicationSerializer, ApplicationListSerialzer, \
        MessageSerializer, SearchSerializer, \
        FeedbackSerializer, FeedbackListSerializer


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

    @transaction.atomic
    def perform_destroy(self, instance):
        applications = Application.objects.filter(session=instance)
        message = "you application to \"%s\" is deleted" % instance.title
        for app in applications:
            send_message(instance.tutor, app.applicant, message)
        super().perform_destroy(instance)


class ApplicationPermission(BasePermission):
    def has_object_permission(self, request, view, obj):
        action = view.action
        user = request.user
        if obj.session.tutor == user: # tutor's view
            return action in ('retrieve', 'update', 'partial_update')
        elif obj.applicant == user: # tutee's view
            return action in ('retrieve', 'destroy')

def send_message(sender, recipient, message, application=None):
        message = Message(
                sender=sender,
                recipient=recipient,
                message=message,
                application=application)
        message.save()

class ApplicationViewSet(viewsets.ModelViewSet):
    permission_classes = (IsAuthenticated, ApplicationPermission)
    #serializer_class = ApplicationSerializer

    def get_queryset(self):
        #print(self.patch, self.partial_update)
        queryset = Application.objects.all()
        action = self.action
        user = self.request.user
        if action == 'list':
            queryset = queryset\
                    .filter(applicant=user)
#                    .annotate(max_message_timestamp=Max('message__timestamp'))\
#                    .order_by('-max_message_timestamp')
        return queryset

    def get_serializer_class(self):
        if self.action == 'list':
            return ApplicationListSerialzer
        return ApplicationSerializer

    @transaction.atomic
    def perform_create(self, serializer):
        user = self.request.user
        session = serializer.validated_data['session']
        if not session.is_open:
            raise exceptions.ValidationError(
                    "the session is closed")
        if Application.objects.filter(
                session=session,
                applicant=user).exists():
            raise exceptions.ValidationError(
                    "duplicate application")
        application = serializer.save(applicant=user)
        if application.accepted != None:
            raise exceptions.ValidationError(
                    "'accepted' must be unset on creation")
        message = "new application"
        send_message(user, application.session.tutor, message, application)

    @transaction.atomic
    def perform_update(self, serializer):
        # Note: only the owner can access this view
        application = serializer.instance
        if 'accepted' in serializer.validated_data:
            accepted = serializer.validated_data['accepted']
            if application.accepted not in (None, accepted):
                raise exceptions.ValidationError(
                        "cannot change the accepted state")
            if accepted is None:
                # this is a no-op
                return
            if accepted:
                session = Session.objects\
                        .filter(pk=application.session.pk)\
                        .select_for_update()\
                        .get()
                if not session.is_open:
                    raise exceptions.ValidationError(
                            "cannot accepted a closed session")
                session.is_open = False
                session.save()
                for app in session.application_set.exclude(pk=application.pk):
                    app.accepted = False
                    app.save()
                    message = "application declined"
                    send_message(self.request.user, app.applicant, message, app)
            message = "application " + (accepted and "accepted" or "declined")
            send_message(self.request.user, application.applicant, message, application)
        serializer.save()

    @transaction.atomic
    def perform_destroy(self, instance):
        if instance.accepted == True:
            session = Session.objects\
                    .filter(pk=instance.session.pk)\
                    .get()
            session.is_open = True
        instance.delete()

class MessageViewSet(mixins.RetrieveModelMixin, mixins.UpdateModelMixin, mixins.ListModelMixin, viewsets.GenericViewSet):
    permission_classes = (IsAuthenticated, )
    serializer_class = MessageSerializer

    def get_queryset(self):
        user = self.request.user
        query = Q(recipient=user)
        queryset = Message.objects.filter(query).order_by('read', '-timestamp')
        return queryset


class FeedbackViewSet(mixins.CreateModelMixin, mixins.RetrieveModelMixin, mixins.ListModelMixin, viewsets.GenericViewSet):
    permission_classes = (IsAuthenticated, )
    queryset = Feedback.objects

    def get_serializer_class(self):
        if self.action == 'list':
            return FeedbackListSerializer
        return FeedbackSerializer

    def get_queryset(self):
        if self.action == 'list':
            user = self.request.user
            queryset = Feedback.objects.filter(session__tutor=user)
        else:
            queryset = Feedback.objects
        return queryset

    @transaction.atomic
    def perform_create(self, serializer):
        user = self.request.user
        feedback = serializer.save()
        session = feedback.session
        app = session.application_set.filter(applicant=user)
        if not app.exists():
            raise exceptions.ValidationError("not allowed")


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
