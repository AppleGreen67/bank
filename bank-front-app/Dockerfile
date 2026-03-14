FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .

COPY accounts/pom.xml accounts/

COPY bank-front-app/pom.xml bank-front-app/
COPY bank-front-app/src bank-front-app/src

RUN mvn clean package -pl bank-front-app

FROM eclipse-temurin:21-jre-alpine

COPY --from=build /app/bank-front-app/target/*.jar bank-front.jar

EXPOSE 8080

CMD ["java", "-jar", "bank-front.jar"]