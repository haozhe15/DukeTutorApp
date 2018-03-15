from django.shortcuts import render
from rest_framework import viewsets
from .models import Session
from .serializers import SessionSerializer
from rest_framework.permissions import IsAuthenticated

# Create your views here.
class TutorSessionViewSet(viewsets.ModelViewSet):
    permission_classes = (IsAuthenticated, )
    serializer_class = SessionSerializer

    def get_queryset(self):
        user = self.request.user
        if user.is_staff:
            queryset = Session.objects.all()
        else:
            queryset = Session.objects.filter(tutor=user)
        return queryset

    def perform_create(self, serializer):
        serializer.save(tutor=self.request.user)
