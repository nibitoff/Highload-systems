FROM maven:3.8.4-openjdk-17 as build

COPY ./ /boozymaven
WORKDIR "/boozymaven"
RUN mvn clean package -Dmaven.test.skip

ENTRYPOINT ["java", "-jar", "/boozymaven/target/boozycalc-0.0.1-SNAPSHOT.jar"]
