FROM eclipse-temurin:21-jdk-alpine
LABEL maintainer="https://github.com/Bkr4wczynski"
WORKDIR /app
COPY target/authentication-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8900
ENTRYPOINT ["java","-jar","app.jar"]