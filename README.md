# I am biker Beta 1.1.0

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

## Further plans
I was planning to make app easier to setup by using docker-compose.yaml but this turned out to be quite challenging
for me and I was unfortunately not capable for using this technology. I still have dockerfiles which allow
creating containers but there is no orchestration and dockerization has to be done manually.

## Videos
Feel free to watch video about setting up project and another one about how it works from user perspective.

