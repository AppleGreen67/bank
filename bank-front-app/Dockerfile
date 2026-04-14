FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY openapi.yaml .

COPY accounts/pom.xml accounts/
COPY gateway/pom.xml gateway/
COPY cash/pom.xml cash/
COPY transfer/pom.xml transfer/
COPY notification/pom.xml notification/
COPY notification-model/pom.xml notification-model/
COPY notification-client/pom.xml notification-client/

COPY bank-front-app/pom.xml bank-front-app/

RUN mvn dependency:go-offline -B

COPY bank-front-app/src bank-front-app/src

RUN mvn clean package -pl bank-front-app -DskipTests=true

FROM eclipse-temurin:21-jre-alpine

COPY --from=build /app/bank-front-app/target/*.jar bank-front.jar

EXPOSE 8080

CMD ["java", "-jar", "bank-front.jar"]