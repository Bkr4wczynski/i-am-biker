# I am biker Beta 1.1.0

## About project
The goal of project was to write java microservice application. Because riding a motorcycle is my passion I decided to write full-stack web app,
that could help motorcyclist organizing their stuff.

## Technicals
Project has been writen using java 24 and spring boot 3 framework. It uses thymeleaf javascript and css for frontend that is connected with java backend.
Java backend code is responsible for connecting to database and for providing JWT security. Application has microservices structure which allows to
easily scale it and develop. The database used by me is MySql 8 however the project structure allows to connect another database by shifting configuration files.
The build tool is maven 3.

## Current features
Project is in it's first beta version. So far it's mainly assets is well organized backend structure. The database used is relational SQL DB.
Projects allows users to login and register and to perform CRUD operations with their bikes, each assigned to it's user. The app is secured
using JWT with cookie storage. I am looking forward to implement https connection to make app much more secured.
The website allows users also to manage the maintenance and calculates intervals for maintenance tasks based on current mileage.

## Contributing
Anyone who is interested in my project is welcomed to contribute. I am open for any suggestions how can I improve my code.

## Project architecture
Project architecture is based on microservices
- Naming server is microservice responsible for load balancing and connecting microservices together
- Api gateway is microservice that all http requests including both mvc and rest calls go through. It ensures that requests are properly authenticated.
- Authentication service is service responsible for generating JWT. If the users credential matches database data JWT is generated. It also provides
connection to other microservices.
- Web service is main microservice that is responsible to displaying both static and that included in database web content for
authenticated users. It also has login and registration forms that are connected to authentication service.
- Maintenance service is small microservice connected to web service that is responsible for calculating maintenance tasks interval.

