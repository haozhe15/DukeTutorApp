from django.shortcuts import render

# Create your views here.
from django.contrib.auth.models import User, Group
from rest_framework import viewsets
from .serializers import UserSerializer
from rest_framework.permissions import IsAuthenticated, BasePermission

class UserPermission(BasePermission):
    def has_object_permission(self, request, view, obj):
        action = view.action
        user = request.user
        if obj == user: # tutor's view
            return action in ('retrieve', 'update', 'partial_update')
        return action in ('retrieve', )

class UserViewSet(viewsets.ModelViewSet):
    """
    API endpoint that allows users to be viewed or edited.
    """
    permission_classes = (UserPermission, )
    serializer_class = UserSerializer

    def get_queryset(self):
        user = self.request.user
        queryset = User.objects.all()
        if not user.is_authenticated:
            queryset = User.objects.none()
        elif not user.is_staff and self.action == 'list':
            queryset = User.objects.filter(pk=user.id)
        return queryset
