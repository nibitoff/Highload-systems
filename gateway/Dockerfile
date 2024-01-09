FROM gradle:8.5.0-jdk17
WORKDIR /app

RUN apt-get update
RUN apt-get install net-tools
RUN apt-get install -y iputils-ping
RUN apt-get -y install curl

COPY build.gradle settings.gradle ./
COPY src ./src
RUN gradle build
CMD gradle bootRun
