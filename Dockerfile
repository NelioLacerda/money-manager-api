FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /workspace

COPY pom.xml .
RUN mvn -B -DskipTests dependency:go-offline

COPY src ./src
RUN mvn -B -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /workspace/target/moneymanager_api-0.0.1-SNAPSHOT.jar moneymanager_api.jar

EXPOSE 9090
ENTRYPOINT ["java","-jar","moneymanager_api.jar"]