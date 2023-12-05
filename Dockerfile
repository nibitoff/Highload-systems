# FROM maven:3.8.4-openjdk-17 as build

# COPY ./ /boozymaven
# WORKDIR "/boozymaven"
# RUN mvn clean package,

# ENTRYPOINT ["java", "-jar", "/boozymaven/target/boozycalc-0.0.1-SNAPSHOT.jar"]
# WORKDIR "/boozymaven"

# RUN ./mvnw clean test
# CMD ["./mvnw", "spring-boot:run"]

FROM eclipse-temurin:17-jdk-jammy as base
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve
COPY src ./src

FROM base as test
RUN ["./mvnw", "test"]

FROM base as development
CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.profiles=prod"]

FROM base as build
RUN ./mvnw package

FROM eclipse-temurin:17-jre-jammy as production
EXPOSE 8080
COPY --from=build /app/target/boozycalc-*.jar /boozycalc.jar
CMD ["java", "-jar", "/boozycalc.jar"]
