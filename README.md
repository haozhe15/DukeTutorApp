# Support Manual

## Description

Peer-to-peer tutoring has been shown to be one of the most effective ways for students to learn, but currently, there aren’t many services that help facilitate this.  This peer tutoring mobile android app aims at Duke community and surrounding Durham community and helps match peer tutors with peer tutees at ease. 

When first using, one can log in using the Duke NetID or other social media account or create a new account. Then the software will collect the user's primary information, including identity, interested fields and available time.  After setting up, one can post a listing with a subject/time/location of a tutoring session or set up the filter to browse other tutors' offerings from the nearest ones. This app also has a payment system for the tutor to price their help. 

The tutee can directly contact the interested tutor via a private chat system. The tutor and tutee will discuss and decide the final time and location to meet.  When tutee quit the chat window, a window should pump up asking whether they have an agreement or not to keep track of history, as well as to offer information to the feedback system. Tutors and tutees can leave ratings and evaluations to each other. The tutor and tutee time will also be recorded and shown to others for reference.

The app is developed in Java language and released on the Google Play app store.  The user data is stored and arranged on a Linux server system, so the app needs to communicate with the server to fetch data to the user.

## Installation

### Android Environment

API 15: Android 4.0.3 (IceCreamSandwich)

### Install and Run

Open the app in Android Studio, click /Build/Rebuild Project and then /Run/Run'App'. Then you can run it on either the Virtual Machine on Android Studio or the andriod mobile phone plugged in by yourself.
For detail information about installing backend, see info below.

## Backend OverView

We use Django REST framework for the backend.
Refer to http://djangoproject.com/ and http://www.django-rest-framework.org/
for documentation.

### Set up

We put the application server in Docker containers.
To set up the server, run

    docker-compose up --build

as __root__ in the directory which contains `docker-compose.yaml`.

If you make modifications to the code, stop the containers and re-run
the above command to apply the changes.

### Code structure

The backend code is arranged in several directories:

* `duketutor/`: Project-related settings.
* `users/`: Logics related to user accounts.
* `tutoring/`: Logics related to tutoring sessions, applications,
messages, etc.

The most interesting logics are located in `tutoring/`, and the code
is arranged by Django convention:

* `models.py`: Database model definitions.
* `serializers.py`: Classes for (un)serializing model objects from/to JSON.
* `views.py`: The actual code that deals with HTTP requests.

### API

Django REST framework provides a *browsable API*.
Once you have the application server running, point your browser to
`host:8000/<API-path>/` to see the API formats and make requests.

For example, at `/sessions/` you can list sessions and
create new sessions; at `/sessions/<id>/` you can view
an existing session and modify or delete it.

Note that `/search/` is not exposed and you need to manually type it in URL.

For the Android app to use these APIs, simply use the same URLs, and
the application will automatically use JSON format
instead of the web interface.

### Testing

Test cases are written in the `tests/` directory.
To run the tests in the Docker container, run

    docker-compose run duketutor ./manage.py test

as **root**.  You can also run the tests locally, but you need to properly
set up the database (e.g., modify `duketutor/settings.py` for database
connection settings).





## Frontend Overview

We use Android Studio with Java language to develop front.
Reference: https://developer.android.com/guide/index.html

### Set Up

Open Android Studio, click on `build/rebuild project`, wait for building finished and then click on "run".

### Overall Structure of Code

If you get our code from Gitlab, the code for Frontend is under the folder named ‘app’. Go to the path: app/src/main/java/com/, you will find all the java code of Frontend.
There are 2 main directories:

* `example.yunjingliu.tutorial` : mainly functional code interacting with users
  * `helper_class/`: providing Backend interaction abstraction class, our own customised ErrorListener class as well as all kinds of Adapter classes.
  * `registration_and_login/` : providing login and registration functionalities
  * `navigation/`: providing all the fragment class related to the navigation bar design
  * `otherActivities/` : providing other activities invoked inside a fragment class, for example: feedback activity or message detail activity.

* `zr/` : mainly assisting classes providing our authorisation information throughout the whole system and provide different kind of translation of JSON or other staffs.
  * `auth/` : provide authorisation interface to the backend, as well as any JSON request formality classes.
  * `forms/`: dealing with the forms that needed to be filled out by user in any PUT method.
  * `json/`: providing an abstracted class to deal with JSON response from backend.

### Pointers to additional files

All of our documents are under path `/documents`.

### Pointers to automated test for Frontend

On our gitlab, under the path `/app/src/androidTest/java/com/example/yunjingliu/tutorial`, you will find our testcases for login and registration validation. 

To run the testcases, open it on Android Studio, follow the steps:

* Right click the test class, then select "run…"
* Choose the android device to run the test class
* See the results to see whether all the tests pass.
