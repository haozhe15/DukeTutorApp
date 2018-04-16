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
    def test_fulltext_search(self):
        session = Session.objects.create(
                title="Software engineering is awesome!",
                description="ECE 651 Project",
                day="Sun",
                time="12:00",
                place="Hudson Hall",
                tutor=self.user)
        queryset = Session.objects.filter(search_vector='software')
        self.assertIn(session, queryset)
        queryset = Session.objects.filter(search_vector='engineer')
        self.assertIn(session, queryset)
        queryset = Session.objects.filter(search_vector='651 ece')
        self.assertIn(session, queryset)
        queryset = Session.objects.filter(search_vector='project')
        self.assertIn(session, queryset)

class ApplicationTestCase(TestCase):
    def setUp(self):
        self.user = User.objects.create(username='xyz')
        self.session = Session.objects.create(
                title="test",
                description='test session',
                day='Sun',
                time='12:00',
                place='Hudson Hall',
                tutor=self.user)
    def test_good_application(self):
        application = Application.objects.create(session=self.session, applicant=self.user, accepted=None)
        self.assertIsNone(application.accepted)
    def test_bad_application(self):
        with self.assertRaises(Exception):
            Application.objects.create(session=self.session)

class MessageTestCase(TestCase):
    def setUp(self):
        self.user = User.objects.create(username='xyz')
        self.session = Session.objects.create(
                title="test",
                description='test session',
                day='Sun',
                time='12:00',
                place='Hudson Hall',
                tutor=self.user)
        self.application = Application.objects.create(session=self.session, applicant=self.user, accepted=None)
    def test_good_message(self):
        message = Message.objects.create(sender=self.user, recipient=self.user, application=self.application, message='xyzzy')
        self.assertEqual(message.sender, self.user)
        self.assertEqual(message.recipient, self.user)
        self.assertEqual(message.application, self.application)
        self.assertFalse(message.read)

        message = Message.objects.create(recipient=self.user, application=self.application, message='xyzzy')
        self.assertIsNone(message.sender)
    def test_bad_message(self):
        with self.assertRaises(Exception):
            message = Message.objects.create(sender=self.user, application=self.application, message='xyzzy')
        with self.assertRaises(Exception):
            message = Message.objects.create(sender=self.user, recipient=self.user, message='xyzzy')
        with self.assertRaises(Exception):
            message = Message.objects.create(sender=self.user, recipient=self.user, application=self.application)


class FeedbackTestCase(TestCase):
    def setUp(self):
        self.user = User.objects.create(username='xyz')
        self.session = Session.objects.create(
                title="test",
                description='test session',
                day='Sun',
                time='12:00',
                place='Hudson Hall',
                tutor=self.user)
    def test_good_feedback(self):
        feedback = Feedback.objects.create(session=self.session, content='xyzzy', rating=3.5)
        self.assertEqual(feedback.session, self.session)
        self.assertEqual(feedback.content, 'xyzzy')
        self.assertEqual(feedback.rating, 3.5)
    def test_bad_message(self):
        with self.assertRaises(Exception):
            feedback = Feedback.objects.create(session=self.session)
        with self.assertRaises(Exception):
            feedback = Feedback.objects.create(session=self.session, content='xyzzy', rating='xyzzy')
