FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .

COPY accounts/pom.xml accounts/
COPY bank-front-app/pom.xml bank-front-app/
COPY cash/pom.xml cash/
COPY transfer/pom.xml transfer/

COPY gateway/pom.xml gateway/

RUN mvn dependency:go-offline -B

COPY gateway/src gateway/src

RUN mvn clean package -pl gateway

FROM eclipse-temurin:21-jre-alpine

COPY --from=build /app/gateway/target/*.jar gateway.jar

EXPOSE 8080

CMD ["java", "-jar", "gateway.jar"]