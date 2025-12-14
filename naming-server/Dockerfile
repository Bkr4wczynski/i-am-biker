FROM eclipse-temurin:21-jdk-alpine
LABEL maintainer="https://github.com/Bkr4wczynski"
WORKDIR /app
COPY target/naming-server-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","app.jar"]