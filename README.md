# 🐾 PawBook – Backend

> REST API for a dog grooming booking platform. Clients register, add their pets and book appointments; the groomer manages services, prices, working days, bookings and the website content from a dashboard.

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4-6DB33F?style=flat-square&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-blue?style=flat-square&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/Auth-JWT-black?style=flat-square)

Frontend: [pawbook-frontend](https://github.com/DimitarKlifov/pawbook-frontend) (React + Vite + Tailwind)

---

## 💡 About

I originally built this as a booking system for a local dog groomer. The project was not adopted, so I removed the business branding and published it as a portfolio project.

---

## ✨ Features

- **Authentication** – registration with **email verification**, login with **JWT**, password reset via emailed link (expires after 1 hour), BCrypt password hashing
- **Role-based access** – `CLIENT` and `GROOMER` roles enforced with Spring Security
- **Pets** – clients manage their dogs (breed, size, notes)
- **Services & pricing** – each service has a price and duration **per dog size** (small / medium / large), with names in Macedonian and English
- **Availability** – free time slots are calculated from the groomer's working days, opening hours, existing bookings and the service duration
- **Bookings** – clients book and cancel; the groomer sees all bookings with statuses `PENDING → CONFIRMED → COMPLETED / CANCELLED`
- **Gallery** – photo uploads to **Cloudinary**, with breed filtering; groomer-only upload and editing
- **Editable website content** – the groomer can change homepage images and text from the dashboard
- **Validation & error handling** – Bean Validation on DTOs and a global exception handler returning consistent JSON errors

---

## 🏗️ Architecture

```
controllers/   REST endpoints (thin, delegate to services)
services/      business logic (availability, booking rules, auth, uploads)
repositories/  Spring Data JPA repositories
entities/      JPA entities: User, Pet, Groomer, GroomingService, ServicePricing,
               Booking, WorkDay, GalleryPhoto, SiteImage
dto/           request / response objects (entities are never exposed directly)
config/        security, JWT filter, CORS, Cloudinary, seed data, exception handler
```

---

## 📡 API Overview

| Area | Endpoints | Access |
|---|---|---|
| Auth | `POST /auth/register` · `GET /auth/verify` · `POST /auth/login` · `POST /auth/forgot-password` · `POST /auth/reset-password` · `GET/PUT /auth/me` | public / logged in |
| Services | `GET /services` · `GET /services/{id}` · `POST/PUT/DELETE /services/{id}` · `POST /services/{id}/pricing` | read: public · write: groomer |
| Availability | `GET /availability` | public |
| Work days | `GET /workdays` · `GET /workdays/{date}` · `PUT /workdays` | read: public · write: groomer |
| Bookings | `POST /bookings` · `GET /bookings/mine` · `PUT /bookings/{id}/cancel` · `GET /bookings` | client · groomer (all) |
| Pets | `GET/POST /pets` · `GET/PUT/DELETE /pets/{id}` | client |
| Gallery | `GET /gallery` · `POST /gallery` · `PUT/DELETE /gallery/{id}` | read: public · write: groomer |
| Site images | `GET /site-images` · `POST /site-images/{slot}` · `PUT /site-images/{slot}/text` | read: public · write: groomer |

---

## 🛠️ Running Locally

**Requirements:** Java 21+, PostgreSQL

1. Create a database:
   ```sql
   CREATE DATABASE pawbook;
   ```
2. Set the environment variables:

   | Variable | Purpose |
   |---|---|
   | `DB_PASSWORD` | PostgreSQL password (user `postgres`) |
   | `JWT_SECRET` | secret used to sign JWT tokens |
   | `MAIL_USERNAME`, `MAIL_PASSWORD` | SMTP account for verification / reset emails |
   | `CLOUDINARY_CLOUD_NAME`, `CLOUDINARY_API_KEY`, `CLOUDINARY_API_SECRET` | image uploads |

3. Run:
   ```bash
   cd booking
   ./mvnw spring-boot:run
   ```

The API starts on `http://localhost:8080`. On the first run, `DataSeeder` creates a groomer account, sample clients, pets, services and prices for testing (see `config/DataSeeder.java`).

---

## 🔭 Possible Improvements

- Move hard-coded URLs (CORS origins, email links) to configuration
- Integration tests for the availability and booking rules
- Docker Compose setup for the API + PostgreSQL
- Email reminders before appointments
