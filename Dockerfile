FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/moneymanaer_api-0.0.1-SNAPSHOT.jar moneymanaer_api.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "moneymanager-v1.0.jar"]