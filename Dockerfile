FROM openjdk:11-jre-slim
COPY ./target/*.jar /app/stationManagement.jar
WORKDIR /app
ENTRYPOINT ["sh", "-c", "java -jar stationManagement.jar"]
