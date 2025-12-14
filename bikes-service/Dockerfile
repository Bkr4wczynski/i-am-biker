FROM eclipse-temurin:21-jdk-alpine
LABEL maintainer="https://github.com/Bkr4wczynski"
WORKDIR /app
COPY target/bikes-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]