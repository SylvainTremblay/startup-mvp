FROM alpine:3.14

RUN apk update && apk add python3 py3-pip
RUN apk add --no-cache bash
RUN python3 -m pip install --user cookiecutter==2.6.0

RUN ln -s  /root/.local/bin/cookiecutter /usr/local/bin/cookiecutter

