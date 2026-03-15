FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY openapi.yaml .

COPY bank-front-app/pom.xml bank-front-app/
COPY gateway/pom.xml gateway/

COPY accounts/pom.xml accounts/

RUN mvn dependency:go-offline -B

COPY accounts/src accounts/src

RUN mvn clean package -pl accounts

FROM eclipse-temurin:21-jre-alpine

COPY --from=build /app/accounts/target/*.jar accounts.jar

EXPOSE 8080

CMD ["java", "-jar", "accounts.jar"]