from django.contrib.auth.models import User
from rest_framework import serializers


class UserSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = User
        fields = ('url', 'username', 'password', 'email', 'first_name', 'last_name')
        extra_kwargs = {'password': {'write_only': True}}

    def create(self, data):
        password = data['password']
        user = super().create(data)
        user.set_password(password)
        user.save()
        return user

    def update(self, obj, data):
        obj = super().update(obj, data)
        if 'password' in data:
            obj.set_password(data['password'])
            obj.save()
        return obj
