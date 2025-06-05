#!/bin/sh

psql -U postgres -c "CREATE USER smvp_admin WITH PASSWORD 'vy%bX*uWt2?0jXqv-1X+';"
psql -U postgres -c "CREATE DATABASE smvpdb OWNER smvp_admin;"