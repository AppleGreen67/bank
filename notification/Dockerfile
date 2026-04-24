FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY openapi.yaml .

COPY bank-front-app/pom.xml bank-front-app/
COPY gateway/pom.xml gateway/
COPY accounts/pom.xml accounts/
COPY cash/pom.xml cash/
COPY transfer/pom.xml transfer/
COPY notification-client/pom.xml notification-client/

COPY notification-model/src notification-model/src
COPY notification-model/pom.xml notification-model/

COPY notification/pom.xml notification/

RUN mvn dependency:go-offline -B

RUN mvn clean install -pl notification-model -am -DskipTests=true

COPY notification/src notification/src

RUN mvn clean package -pl notification -Pdocker-build -DskipTests=true

FROM eclipse-temurin:21-jre-alpine

COPY --from=build /app/notification/target/*.jar notification.jar

EXPOSE 8080

CMD ["java", "-jar", "notification.jar"]