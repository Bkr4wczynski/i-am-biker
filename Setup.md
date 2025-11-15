# Project setup

## 1. Download code
Download code from github and open it in your IDE. Application is build using many other apps that are microservices so everything that is downloaded
should be opened in your IDE.

## 2. Setup database
You should setup database using the queries that you can find in queries.txt file. Note that queries are written using Mysql. Very important is to set 
your username as biker. Otherwise you can change the code properties to use any username.

## 3. Setup environmental variables
In your IDE you should specify two variables.
First is called **DB_PASSWORD** that should contain your password to database.
Second one is **JWT_SECRET** which is 64 characters long secret. It is recommended to use online jwt secrets generators for this step.

## 4. Run code in proper order
Note that naming server and api gateway are server apps and rest is client side. Simply you should run naming server, then api gateway and where those are build
run the rest of services. To access app type in your browser : http://localhost:8765/web/my-profile and if you are not logged in, the app will redirect you to login page.
