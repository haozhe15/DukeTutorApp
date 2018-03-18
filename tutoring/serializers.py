from rest_framework import serializers
from .models import Session, Application, Message

class SessionSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Session
        fields = ('url', 'title', 'description', 'day', 'time', 'place', 'tutor')
        read_only_fields = ('tutor', )
    def save(self, **kwargs):
        super().save(**kwargs)

class ApplicationSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Application
        fields = ('url', 'session', 'applicant', 'accepted')
        read_only_fields = ('applicant', )

class MessageSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Message
        fields = ('url', 'sender', 'recipient', 'application', 'message', 'timestamp')
        read_only_fields = ('sender', 'recipient', 'application', 'message')

class SearchSerializer(serializers.Serializer):
    keyword = serializers.CharField()
