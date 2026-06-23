Transaction API
A Spring Boot API for creating and managing transactions with validation, status transitions, and transactional outbox support for reliable event publishing.

Features
Create transactions with request validation
Approve and decline transactions
Custom class-level validation for transaction totals
Global exception handling with structured error responses
Transactional outbox pattern for reliable event publishing
Scheduled outbox publisher

Tech Stack :Java 17, Spring Boot, Spring Web, Spring Validation, JPA, H2, Kafka support

APIs:
1) POST /api/v1/transactions
RequestBody
{
  "saleAmount": 100.00,
  "commissionAmount": 10.00,
  "parts": [
    {
      "saleAmount": 60.00,
      "commissionAmount": 6.00
    },
    {
      "saleAmount": 40.00,
      "commissionAmount": 4.00
    }
  ]
}
Validation Rules
saleAmount & commissionAmount must not be null and must be >= 0.00
transaction parts must not be empty
Sum of part sale amounts must equal the request saleAmount and sum of part commission amounts must equal the request commissionAmount.

3) GET /api/v1/transactions/{{id}}/approve: update to DB and Outbox pattern used to ensure database changes and event creation happen atomically without failure.
4) GET /api/v1/transactions/{{id}}/decline: update to DB and Outbox pattern used to ensure database changes and event creation happen atomically without failure.

Shared Postman script for testing.
To run the application:
mvn spring-boot:run

H2 DB can be accessed once server is up at [/h2-console](http://localhost:8080/h2-console)
