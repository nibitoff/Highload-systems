FROM gradle:8.5.0-jdk17-alpine
WORKDIR /app
COPY build.gradle settings.gradle ./
COPY src ./src
RUN gradle build
CMD gradle bootRun
