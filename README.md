# I am biker 2.0.0

## About project
The goal of project was to write java microservice application. Because riding a motorcycle is my passion I decided to write full-stack web app,
that could help motorcyclist organizing their stuff. Project has forum and allows to perform CRUD operations with bikes.

## Feedback
Any constructive critic about what can I improve is much appreciated. You can write to me on github or contact me at
bkrawczynski@protonmail.com

## Current structure
Currently in version 2.0.0 there are seven services

- naming server - eureka server for load balancing and registering services
- api gateway - routes all request to 8765 port and perform authentication if necessary
- authentication-service - service used by api-gateway to authenticate requests
- bikes-service - service for performing CRUD operations on users' motorcycles
- maintenance-service - generates maintenance for bikes nu their mileage
- forum-service - connected to separate database. Responsible for CRUD operations on forum threads
- web-service - thymeleaf based frontend for displaying app functionality on website

All these microservices work together and use REST API to communicate.

## Setup
The project is provided with docker-compose.yaml file for easy setup.
In order to set up project first run
mvn clean install on parent pom. Then run docker compose up --build command to run the project.
You can also setup project using localhost. Instructions for setting up project with localhost are provided in setup.md
