# 🌍 FollowMe

Run application in dev mode via cmd 
```bash
mvn clean spring-boot:run -Dspring-boot.run.profiles=dev
```

## Technical stack

System for booking excursions in FollowMe agency.

Uses next Spring Projects: 
- Boot
- Data

Used libraries 
- Lombok
- H2 (for internal database)

## Project functionality

Existing functionality
- [Get list of all excursions in web](http://localhost:8080/excursions)
- Get excursion by id (better to navigate from previous one)

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