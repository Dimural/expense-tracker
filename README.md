# Expense Tracker API
Spring Boot backend for personal budgeting and expense tracking with JWT authentication, category management, transaction CRUD, and monthly analytics.

## Features
- JWT-based auth with register/login and stateless request filtering
- User-scoped categories (income/expense) and validated transaction CRUD
- Monthly analytics (income, expense, net, category breakdown)
- PostgreSQL persistence via Spring Data JPA/Hibernate
- Maven build with Java 17 and Spring Boot 3.5

## Tech Stack
- Java 17, Spring Boot 3.5 (Web, Data JPA, Security, Validation)
- PostgreSQL, Hibernate
- JSON Web Tokens (jjwt)
- Maven (wrapper included)

## Prerequisites
- JDK 17+
- PostgreSQL running locally
- Maven (or use the provided `./mvnw` wrapper)

## Configuration
The app reads settings from `application.properties` (override with environment variables if preferred). Minimum settings to provide:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/expensetracker
spring.datasource.username=fintrack
spring.datasource.password=javaproject1

jwt.secret=change-me-to-a-long-random-string
jwt.expirationMs=86400000   # 24h

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true    # optional
```

Environment variable equivalents: `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`, `JWT_SECRET`, `JWT_EXPIRATIONMS`.

## Run Locally
```bash
# install deps and start the API on http://localhost:8080
./mvnw spring-boot:run

# or build a runnable jar
./mvnw clean package
java -jar target/expensetracker-0.0.1-SNAPSHOT.jar
```

## Quick API Guide
All `/api/**` routes require `Authorization: Bearer <token>` from `/auth/login`.

### Auth
- `POST /auth/register` – `{ "email": "...", "password": "..." }`
- `POST /auth/login` – returns `{ "message": "...", "token": "<jwt>" }`

### Categories
- `POST /api/categories` – create `{ "name": "Food", "type": "EXPENSE" }`
- `GET /api/categories` – list categories for the authenticated user

### Transactions
- `GET /api/transactions` – list transactions (sorted by date desc)
- `POST /api/transactions` – create `{ "amount": 25.50, "date": "2024-12-12", "description": "Lunch", "categoryId": "<uuid>" }`
- `GET /api/transactions/{id}` – fetch a single transaction
- `PUT /api/transactions/{id}` – partial update (any of amount/date/description/categoryId)
- `DELETE /api/transactions/{id}` – remove a transaction

### Analytics
- `GET /api/analytics/monthly?year=2024&month=12` – totals plus per-category breakdown for the month

## Sample Flow (cURL)
```bash
# register
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"you@example.com","password":"StrongPass1!"}'

# login
TOKEN=$(curl -s -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"you@example.com","password":"StrongPass1!"}' | jq -r '.token')

# create a category
curl -X POST http://localhost:8080/api/categories \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name":"Groceries","type":"EXPENSE"}'

# create a transaction
curl -X POST http://localhost:8080/api/transactions \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"amount":42.15,"date":"2024-12-10","description":"Market","categoryId":"<category-uuid>"}'
```

## Project Layout
- `src/main/java/com/expensetracker/expensetracker/auth` – JWT auth, filter, controller
- `src/main/java/com/expensetracker/expensetracker/category` – category domain + REST
- `src/main/java/com/expensetracker/expensetracker/transaction` – transaction domain + REST
- `src/main/java/com/expensetracker/expensetracker/analytics` – monthly analytics calculations
- `src/main/resources/application.properties` – default configuration

## Testing
```bash
./mvnw test
```

## Notes
- Requests are stateless; sessions are disabled.
- Data is user-scoped in services/repositories; include the bearer token for every protected request.
- Hibernate auto DDL is set to `update` for local dev; adjust before production.
