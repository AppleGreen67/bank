FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .

COPY bank-front-app/pom.xml bank-front-app/

COPY accounts/pom.xml accounts/
COPY accounts/src accounts/src

RUN mvn clean package -pl accounts

FROM eclipse-temurin:21-jre-alpine

COPY --from=build /app/accounts/target/*.jar accounts.jar

EXPOSE 8080

CMD ["java", "-jar", "accounts.jar"]