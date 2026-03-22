FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY openapi.yaml .

COPY bank-front-app/pom.xml bank-front-app/
COPY gateway/pom.xml gateway/
COPY accounts/pom.xml accounts/
COPY cash/pom.xml cash/
COPY notification/pom.xml notification/

COPY transfer/pom.xml transfer/

RUN mvn dependency:go-offline -B

COPY transfer/src transfer/src

RUN mvn clean package -pl transfer -Pdocker-build -DskipTests=true

FROM eclipse-temurin:21-jre-alpine

COPY --from=build /app/transfer/target/*.jar transfer.jar

EXPOSE 8080

CMD ["java", "-jar", "transfer.jar"]