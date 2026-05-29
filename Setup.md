# Project setup

### * For anyone struggling with setup there is a video about setting up project.

## 1. Download code
Download code from github and open it in your IDE. Application is build using many other apps that are microservices so everything that is downloaded
should be opened in your IDE.

## 2. Setup database
Create two databases in MySQL: one is for forum and other one is for authentication and bikes info. You can also
create users for them but you can access it via root. Then move to point 3 and provide essential data.

## 3. Setup environmental variables
In order for application to work, you need to create file .env and setup variables.
- *DB_NAME* - name of your database
- *DB_NAME2* - name of your second database
- *DB_USERNAME* - username for your MySQL user
- *DB_PASSWORD* - password for that user
- *DB_USERNAME2* - username for second MySQL user
- *DB_PASSWORD2* - password for second user
- *JWT_SECRET* - jwt secret for security recommended at least 256 bits

Then you should specify in your ide for each microservice(naming server and maintenance service not required)
to use those environmental variables


## 4. Run code in proper order
First run naming server and wait until it starts. Then run rest of services and wait until everyone of them boot properly.
Then access application by browser http://localhost:8765/web/my-profile you should be redirected to logging page.
Register yourself and you can access app with your account.


