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

### MVC:

Existing functionality
- [Get list of all excursions in web](http://localhost:8080/excursions)
- Get excursion by id ([example link](http://localhost:8080/excursions/4a27e13d-70bd-4aed-b242-e6f9ab4b1186))

### Web Api:

You can use postman collection (`FollowMe.postman_collection.json` file in the root) to see list of available endpoint and their paramters

Available functionality
- See list of excursions
  - Ger excursion by id
  - [USER] Book on excursion (including several participant)
  - [USER] See list of my booked excursions
- [Admin] See list of users
- [Admin] App health state 

To use role required end point, you need to have `Authorization` header in format `Bearer: {{accessToken}}`. 
Access token can be received via login endpoint. 

Future functionality
- More interactions with excursions.
  - Filter by amount of open places, country, price, tags
- Booking excursion
  - Cancel booking
- Admin functionality
  - CRUD for excursion (not it fills only form sql script)
  - Statistics (see which excursions more popular)
  - Send notifications (by emails)

## Database

This project uses H2 in-memory database.

To access the H2 console, you can use the following URL:
```sh
http://localhost:8080/h2-console
```

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