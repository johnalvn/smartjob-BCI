
FROM gradle:8.3-jdk17 AS builder
  
WORKDIR /home/gradle/app  
COPY --chown=gradle:gradle . .
  
RUN gradle clean build -x test
  
FROM openjdk:17-jdk-slim
  
WORKDIR /app
  
COPY --from=builder /home/gradle/app/build/libs/*-SNAPSHOT.jar app.jar
  
EXPOSE 8080
  
ENTRYPOINT ["java", "-jar", "app.jar"]
