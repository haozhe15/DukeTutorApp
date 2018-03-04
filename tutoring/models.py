from django.db import models
from django.contrib.auth.models import User, Group

# Create your models here.

class Session(models.Model):
    DAY_CHOICES = (
        ('Sun', 'Sunday'),
        ('Mon', 'Monday'),
        ('Tue', 'Tuesday'),
        ('Wed', 'Wednesday'),
        ('Thu', 'Thursday'),
        ('Fri', 'Friday'),
        ('Sat', 'Saturday'),
    )
    title = models.CharField(max_length=150)
    description = models.TextField()
    day = models.CharField(max_length=3, choices=DAY_CHOICES)
    time = models.TimeField()
    place = models.CharField(max_length=150)
    tutor = models.ForeignKey(User, on_delete=models.CASCADE, related_name='owned_sessions')
