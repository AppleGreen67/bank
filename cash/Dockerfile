FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY openapi.yaml .

COPY bank-front-app/pom.xml bank-front-app/
COPY gateway/pom.xml gateway/
COPY accounts/pom.xml accounts/
COPY transfer/pom.xml transfer/
COPY notification/pom.xml notification/

COPY notification-model/src notification-model/src
COPY notification-model/pom.xml notification-model/

COPY notification-client/src notification-client/src
COPY notification-client/pom.xml notification-client/

COPY cash/pom.xml cash/

RUN mvn dependency:go-offline -B

RUN mvn clean install -pl notification-model -am -DskipTests=true
RUN mvn clean install -pl notification-client -am -DskipTests=true

COPY cash/src cash/src

RUN mvn clean package -pl cash -Pdocker-build -DskipTests=true

FROM eclipse-temurin:21-jre-alpine

COPY --from=build /app/cash/target/*.jar cash.jar

EXPOSE 8080

CMD ["java", "-jar", "cash.jar"]