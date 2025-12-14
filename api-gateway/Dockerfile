FROM eclipse-temurin:21-jdk-alpine
LABEL maintainer="https://github.com/Bkr4wczynski"
WORKDIR /app
COPY target/api-gateway-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8765
ENTRYPOINT ["java","-jar","app.jar"]