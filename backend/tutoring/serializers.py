from rest_framework import serializers
from .models import Session

class SessionSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Session
        fields = ('url', 'title', 'description', 'day', 'time', 'place', 'tutor')
        read_only_fields = ('tutor', )
    def save(self, **kwargs):
        print(kwargs)
        super().save(**kwargs)
