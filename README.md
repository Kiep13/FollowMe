# 🌍 FollowMe

Run application in dev mode via cmd 
```bash
mvn clean spring-boot:run -Dspring-boot.run.profiles=dev
```

## Technical stack

System for booking excursions in FollowMe agency.

This project build upon Java 17.

Uses next Spring Projects: 
- Boot
  - DI
  - AOP
  - Actuator
- Data
- MVC
- Security

Used libraries 
- Lombok
- H2 (for inmemory database)

## Project functionality

Existing functionality
- [Get list of all excursions in web](http://localhost:8080/excursions)
- Get excursion by id (better to navigate from previous one)
- Book to excursion (development in progress)

Future functionality
- Interacting with excursions.
  - See list of all available excursions
    - Filter by amount of open places, country, price, tags
  - See details of excursion
- Booking excursion
  - See list of all booked excursions
  - Book excursion for several people
  - Cancel booking
- Admin functionality
  - CRUD for excursion
  - Statistics
  - Send notifications

## Actuator

Available edn point (secured by ADMIN role)
- app/health
- app/info
- app/metrics

Custom metrics
- api.calls.excursions,
- api.calls.login
- api.calls.booking

## Tests

To run tests, use the following command:
```bash
mvn test
```