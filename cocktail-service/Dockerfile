FROM eclipse-temurin:17-jdk-jammy as base
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src ./src
CMD ./mvnw package; java -jar /app/target/cocktail-service-0.0.1-SNAPSHOT.jar
