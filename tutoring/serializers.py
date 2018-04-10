from rest_framework import serializers
from .models import Session, Application, Message, Feedback

class SessionSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Session
        fields = ('url', 'title', 'description', 'day', 'time', 'place', 'tutor', 'feedback_set')
        read_only_fields = ('tutor', 'feedback_set')

class ApplicationSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Application
        fields = ('url', 'session', 'applicant', 'accepted')
        read_only_fields = ('applicant', )

class ApplicationListSerialzer(serializers.HyperlinkedModelSerializer):
    session = SessionSerializer()
    class Meta:
        model = Application
        fields = ('url', 'session', 'applicant', 'accepted')
        read_only_fields = ('applicant', )

class MessageSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Message
        fields = ('url', 'sender', 'recipient', 'application', 'message', 'timestamp', 'read')
        read_only_fields = ('sender', 'recipient', 'application', 'message')

class FeedbackSerializer(serializers.HyperlinkedModelSerializer):
    session = SessionSerializer()
    class Meta:
        model = Feedback
        fields = ('url', 'session', 'content', 'rating')

class SearchSerializer(serializers.Serializer):
    keyword = serializers.CharField()
