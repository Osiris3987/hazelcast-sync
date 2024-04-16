FROM maven:3.8.5-openjdk-17 AS builder
COPY ./ ./
RUN mvn clean package -DskipTests
FROM openjdk:17-jdk-slim
COPY --from=builder target/hackathon_becoder_backend-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]