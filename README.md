# I am biker — v2.0.0

> A full-stack Java microservice web application for motorcyclists.  
> Organize your bikes, track maintenance, and connect with fellow riders on the forum.

---

[![I Am Biker – demo](https://img.youtube.com/vi/8lUscGS6LKE/maxresdefault.jpg)](https://www.youtube.com/watch?v=8lUscGS6LKE)
*▶ Short demo of project*

## 📖 About

Riding a motorcycle is more than just a hobby — it's a passion. **I Am Biker** was built to help motorcyclists keep their world organized in one place. The app supports a community forum and full CRUD management of your motorcycles.

---

## 🏗️ Architecture

The application is composed of **seven independent microservices**, all communicating via REST API:

| Service | Description |
|---|---|
| 🔭 **Naming Server** | Eureka server — responsible for registrating services and load balancing |
| 🚦 **API Gateway** | Routes all requests through port `8765` and perform authentication if it is required |
| 🔐 **Authentication Service** | Used by the API Gateway to authenticate incoming requests |
| 🏍️ **Bikes Service** | Full CRUD operations on users' motorcycles |
| 🔧 **Maintenance Service** | Generates maintenance schedules based on bike mileage |
| 💬 **Forum Service** | Handles CRUD operation for i-am-biker forum |
| 🌐 **Web Service** | Service based on thymeleaf for displaying content to user on web |

---

## ⚙️ Setup

### 1. Configure environment variables

Before running the project, create a `.env` file in the root directory. All values are required:

```env
DB_NAME=database
DB_USERNAME=username
DB_PASSWORD=password
DB_ROOT_PASSWORD=root
JWT_SECRET=secure-jwt-secret
```

### 2. Run with Docker (recommended)

```bash
# Build all modules
mvn clean install

# Start all services
docker compose up --build
```

### Localhost

Detailed localhost setup instructions are available in [`setup.md`](setup.md).

---

## 💬 Feedback

Constructive criticism is always welcome!

- 🐙 **GitHub** — open an issue or pull request
- 📧 **Email** — [bkrawczynski@protonmail.com](mailto:bkrawczynski@protonmail.com)
