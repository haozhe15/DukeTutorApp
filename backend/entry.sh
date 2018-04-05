#!/bin/sh

./manage.py collectstatic --noinput && \
./manage.py makemigrations --noinput && \
./manage.py migrate --noinput && \
exec uwsgi --ini uwsgi.ini
