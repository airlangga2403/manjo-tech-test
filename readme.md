# Payment Gateway Technical Test

## Technology Stack

### Backend

* Java 21
* Spring Boot 3
* PostgreSQL
* Spring Data JPA
* Swagger OpenAPI

### Frontend

* React + Vite
* Material UI
* Axios

---

## Features

### Generate QRIS

POST `/api/v1/qr/generate`

### QR Payment

POST `/api/v1/qr/payment`

### Payment Notification

POST `/api/v1/payment/notification`

### Merchant Status Tracker

GET `/api/v1/payment/transactions`

GET `/api/v1/payment/status/{referenceNo}`

---

## Running Backend

```bash
mvn clean install
mvn spring-boot:run
```

Backend URL:

```text
http://localhost:8080
```

Swagger:

```text
http://localhost:8080/swagger-ui.html
```

---

## Running Frontend

```bash
cd merchant-tracker

npm install

npm run dev
```

Frontend URL:

```text
http://localhost:5173
```

---

## Database

PostgreSQL

```yaml
POSTGRES_DB=payment_gateway
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
```

Docker:

```bash
docker compose up -d
```
