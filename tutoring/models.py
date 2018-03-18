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

    @transaction.atomic
    def save(self, *args, **kwargs):
        super().save(*args, **kwargs);
        self.update_search_vector()

    def update_search_vector(self):
        self.search_vector = self.SEARCH_VECTOR
        super().save(update_fields=('search_vector', ))

    class Meta:
        indexes = [GinIndex(fields=['search_vector'])]
