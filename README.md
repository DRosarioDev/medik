
<br />
<div align="center">
  <a href="https://github.com/DRosarioDev/medik">
    <img src="https://github.com/DRosarioDev/medik/blob/main/medik%20-%20Frontend/src/assets/medik_logo.png" alt="Logo" width="256" height="256">
  </a>


  <p align="center">
    Full-stack medical office portal built with Angular, Spring Boot and PostgreSQL 
    <br />
  </p>
</div>


## About The Project

**Medik** is a medical practice management portal that provides two distinct interfaces — one for **doctors** and one for **patients** — behind a secure JWT-authenticated login. Doctors can manage patient records and prescriptions, book appointments through a calendar UI, and search their patient list. Patients can view their prescriptions, book appointments with their assigned doctor, see their medical profile, and switch doctors.

In details:

### Doctor
- Search patients by surname and view their full details
- View and create prescriptions with urgency levels (Low / Medium / High)
- Interactive monthly/daily calendar powered by Syncfusion Scheduler
- Create, edit, and delete appointments
- See available time slots for a given date
### Patient
- Personal dashboard with stats: total prescriptions, high-urgency count, assigned doctor
- View all prescriptions with doctor attribution
- Book appointments on the nearest available date or a custom date
- View and change assigned doctor
- Profile page with personal and contact information


### Built With

[![Angular](https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white)](https://angular.io/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)](https://https://hibernate.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white)](https://jwt.io/)
[![Bootstrap](https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white)](https://getbootstrap.com/)


# Getting Started

### Prerequisites

- **Node.js** ≥ 22.16 and **npm** ≥ 10.9
- **Java** 17+
- **Gradle** 8.14+
- **PostgreSQL** 18+ (or use the `mock` profile to skip the database entirely)

### Installation

### Backend

1) Rename

```properties
application.properties.example
```
in

```properties
application.properties
```

2) Insert your db credentials

If you're using the hibernate profile, run the two SQL files inside src/main/resources/database/ to create the tables (schema.sql) and seed them with sample data (insert.sql).

3) Start the server with 
```properties
./gradlew bootRun
```
It will be available at http://localhost:8080.

### Frontend

1) Install dependences with

```properties
npm install
```
2) Create src/environments/environment.ts from the example file, setting the backend URL and your <a href="https://www.syncfusion.com">Syncfusion license key</a> for the appointment calendar. The key is free for personal/portfolio use — you can get one on their website.

3) Start the app with 

```properties
ng serve
```

It will be available at http://localhost:4200

#### Mock Mode (no database)
If you want to test the app without setting up PostgreSQL, set 
```properties
spring.profiles.active=mock
```
in the backend and 
```properties
backendStrategy: 'MOCK'
```
in the frontend environment file. Data is then loaded directly from <strong>RepositoryMock.java</strong>, which comes pre-seeded with 3 doctors, 3 patients, several prescriptions and appointments.

### Default Credentials (Mock / Seed data)
 
| Role | Email | Password |
|---|---|---|
| Doctor | `a@medico.com` | `1234` |
| Doctor | `b@medico.com` | `5678` |
| Patient | `a@paziente.com` | `1234` |
| Patient | `b@paziente.com` | `5678` |
| Patient | `c@paziente.com` | `9012` |


## 🧪 Development Mode (Mock Profile)
 
To run entirely without a database, activate the `mock` Spring profile:
 
```properties
spring.profiles.active=mock
```

The backend will use an in-memory repository pre-seeded with sample doctors, patients, prescriptions, and appointments — no PostgreSQL required.


# Usage

## Doctor side

https://github.com/user-attachments/assets/dc380d29-4a07-4e42-b830-04d32b9b05b8


## Patient side

https://github.com/user-attachments/assets/09963cc9-82f8-45b9-98e6-c6d6490b2425


# Contact

Domenico Rosario Alberti - domenicorosario.alberti@gmail.com

Project Link: [https://github.com/DRosarioDev](https://github.com/DRosarioDev)




