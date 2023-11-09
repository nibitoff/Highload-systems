FROM openjdk:17-alpine
WORKDIR /app
COPY ./target/boozycalc-0.0.1-SNAPSHOT.jar /app
ENTRYPOINT ["java", "-jar", "boozycalc-0.0.1-SNAPSHOT.jar"]