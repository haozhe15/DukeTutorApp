from django.db import models, transaction
from django.contrib.auth.models import User, Group
from django.contrib.postgres.search import SearchVector, SearchVectorField
from django.contrib.postgres.indexes import GinIndex

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
    tutor = models.ForeignKey(User, on_delete=models.CASCADE,
            related_name='owned_sessions')
    search_vector = SearchVectorField(null=True)
    SEARCH_VECTOR = SearchVector('title', weight='A') + \
            SearchVector('description', weight='B')
    is_open = models.BooleanField(editable=False)

    class Meta:
        indexes = [GinIndex(fields=['search_vector'])]

    def __str__(self):
        return self.title

    @transaction.atomic
    def save(self, *args, **kwargs):
        super().save(*args, **kwargs);
        self.update_search_vector()

    def update_search_vector(self):
        self.search_vector = self.SEARCH_VECTOR
        super().save(update_fields=('search_vector', ))

class Application(models.Model):
    session = models.ForeignKey(Session, on_delete=models.CASCADE)
    applicant = models.ForeignKey(User, on_delete=models.CASCADE)
    # three-value logic:
    # True - accepted
    # False - declined
    # None - not decided
    accepted = models.NullBooleanField()

    class Meta:
        # a user cannot apply for the same session more than once
        unique_together = ('session', 'applicant')

class Message(models.Model):
    sender = models.ForeignKey(User, on_delete=models.CASCADE,
            related_name='sent_messages')
    recipient = models.ForeignKey(User, on_delete=models.CASCADE,
            related_name='received_messages')
    application = models.ForeignKey(Application, on_delete=models.CASCADE)
    message = models.TextField()
    timestamp = models.DateTimeField(auto_now=True)
    read = models.BooleanField(default=False)
