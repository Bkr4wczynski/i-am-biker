# 🛠️ Project Setup

## 1. Download code

Download code from GitHub and open it in your IDE. Application is built using many other apps that are microservices so everything that is downloaded should be opened in your IDE.

---

## 2. Setup database

Create database in MySQL: It is necessary for project to be running properly.

---

## 3. Setup environmental variables

In order for application to work, you need to create file `.env` and setup variables:

| Variable | Description |
|---|---|
| `DB_NAME` | Name of your database |
| `DB_USERNAME` | Username for your MySQL user |
| `DB_PASSWORD` | Password for that user |
| `JWT_SECRET` | JWT secret for security — recommended at least 256 bits |

Then you should specify in your IDE for each microservice *(naming server and maintenance service not required)* to use those environmental variables.

---

## 4. Run code in proper order

1. Run **naming server** and wait until it starts.
2. Run the **rest of services** and wait until everyone of them boots properly.
3. Visit the Eureka dashboard to verify all services are connected:
   ```
   http://localhost:8761
   ```
4. Access the application in your browser:
   ```
   http://localhost:8765/web/my-profile
   ```
   You should be redirected to the logging page. Register yourself and you can access the app with your account.
