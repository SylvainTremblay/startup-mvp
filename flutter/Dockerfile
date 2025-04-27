FROM alpine
RUN apk update

## Make sure to install gcompat
RUN apk add bash curl file git unzip which zip gcompat

# # download Flutter SDK from Flutter Github repo
RUN git clone https://github.com/flutter/flutter.git /usr/local/flutter

# # Set flutter environment path
ENV PATH="/usr/local/flutter/bin:/usr/local/flutter/bin/cache/dart-sdk/bin:${PATH}"

# # Run flutter doctor
RUN flutter doctor

# # Enable flutter web
RUN flutter channel master
RUN flutter upgrade
RUN flutter config --enable-web
