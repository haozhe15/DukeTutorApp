from django.shortcuts import render
from rest_framework import views, viewsets, generics, mixins
from .models import Session
from .serializers import SessionSerializer, SearchSerializer
from rest_framework.permissions import IsAuthenticated
from django.contrib.postgres.search import SearchVector
from rest_framework.response import Response

# Create your views here.
class TutorSessionViewSet(viewsets.ModelViewSet):
    permission_classes = (IsAuthenticated, )
    serializer_class = SessionSerializer

    def get_queryset(self):
        queryset = Session.objects.all()
        user = self.request.user
        if self.action == 'list' and not user.is_staff:
            # `list' view limits the sessions
            # to only those owned by the user
            queryset = Session.objects.filter(tutor=user)
        return queryset

    def perform_create(self, serializer):
        serializer.save(tutor=self.request.user)


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
