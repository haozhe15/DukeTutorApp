from django.test import TestCase
from django.db.utils import DataError
import datetime

from tutoring.models import *

class SessionTestCase(TestCase):
    def setUp(self):
        self.user = User.objects.create(username='xyz')
    def test_create(self):
        session = Session.objects.create(
                title="test",
                description='test session',
                day='Sun',
                time='12:00',
                place='Hudson Hall',
                tutor=self.user)
        session = Session.objects.get(pk=session.id)
        self.assertEqual(session.title, 'test')
        self.assertEqual(session.description, 'test session')
        self.assertEqual(session.day, 'Sun')
        self.assertEqual(session.time, datetime.time(12,00))
        self.assertEqual(session.place, 'Hudson Hall')
        self.assertEqual(session.tutor, self.user)
    def test_invalid_day(self):
        with self.assertRaises(DataError):
            Session.objects.create(
                    title="test",
                    description='test session',
                    day='Sunday', # too long
                    time='12:00',
                    place='Hudson Hall',
                    tutor=self.user)
        # TODO: Session object does not validate 'day' field.
