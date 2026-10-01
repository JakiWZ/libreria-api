# Libreria

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.0-brightgreen)
[![CI](https://github.com/JakiWZ/libreria-api/actions/workflows/ci.yml/badge.svg)](https://github.com/JakiWZ/libreria-api/actions/workflows/ci.yml)

**🌐 Language / Lingua:** [English](#english) | [Italiano](#italiano)

---

## English

A backend REST API built with Java and Spring Boot to manage a book catalog (create, read,
update, delete and search books). It follows a clean layered architecture with DTOs, input
validation, centralized error handling with proper HTTP status codes, PostgreSQL with Flyway
migrations, and interactive API documentation via Swagger. Automated tests run on an in-memory
H2 database, so the build is green without any external dependency.

> Built as a study project to consolidate the fundamentals of backend development in Java.

### Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Usage Examples](#usage-examples)
- [Error Handling](#error-handling)
- [Testing](#testing)
- [Roadmap](#roadmap)

### Features

- Full CRUD operations on books
- Search by id, title, author or ISBN
- Input validation (required fields, non-negative price)
- Centralized error handling with consistent JSON responses (400, 404, 409, 500)
- Database schema versioning with Flyway
- Interactive API documentation with Swagger UI
- Automated tests running on an in-memory H2 database

### Tech Stack

- **Java 17**
- **Spring Boot 4.1.0** — Spring Web MVC, Spring Data JPA, Bean Validation
- **PostgreSQL** — main database
- **Flyway** — database migrations
- **H2** — in-memory database for tests
- **Lombok** — boilerplate reduction
- **springdoc / Swagger UI** — API documentation
- **JUnit 5 + Mockito + MockMvc** — testing
- **Maven** — build tool (wrapper included)

### Architecture

The project follows a layered architecture, where each layer has a single responsibility:

```
HTTP Request
     │
     ▼
 Controller   → handles HTTP requests/responses     (LibroController)
     │
     ▼
 Service      → application logic                   (LibroService / LibroServiceImpl)
     │
     ▼
 Repository   → data access (Spring Data JPA)       (LibroRepository)
     │
     ▼
 PostgreSQL
```

Supporting components:
- **Entity** (`Libro`) — maps the `libri` database table
- **DTO** (`LibroDTO`) — object exchanged with the client; holds validation rules and keeps the
  entity from being exposed directly
- **Mapper** (`LibroMapper`) — converts between Entity and DTO
- **GlobalExceptionHandler** — single place that turns exceptions into consistent JSON responses

#### Project Structure

```
src/main/java/com/example/libreria
├── controller   → REST endpoints
├── service      → application logic (interface + implementation)
├── repository   → data access
├── entity       → database tables
├── dto          → client-facing objects + validation
├── mapper       → Entity <-> DTO conversion
├── error        → exception handling and error format
└── config       → Swagger / OpenAPI configuration

src/main/resources
├── application.properties
└── db/migration → Flyway SQL scripts
```

### Getting Started

#### Prerequisites

- Java 17
- A running PostgreSQL instance

#### Configuration

Database credentials are read from environment variables (never hard-coded):

```bash
export DB_URL=jdbc:postgresql://localhost:5432/libreria
export DB_USERNAME=your_user
export DB_PASSWORD=your_password
```

#### Run

```bash
./mvnw spring-boot:run
```

On startup, Flyway automatically creates the `libri` table. The app runs on port `8080`.

API documentation (Swagger UI), once the app is running:

```
http://localhost:8080/swagger-ui.html
```

### API Endpoints

Base path: `/api/libri`

| Method | Endpoint                          | Description                | Responses             |
|--------|-----------------------------------|----------------------------|-----------------------|
| GET    | `/api/libri`                      | List all books             | 200                   |
| GET    | `/api/libri/{id}`                 | Get a book by id           | 200 / 404             |
| GET    | `/api/libri/cerca/titolo?titolo=` | Search by title            | 200                   |
| GET    | `/api/libri/cerca/autore?autore=` | Search by author           | 200                   |
| GET    | `/api/libri/cerca/isbn?isbn=`     | Search by ISBN             | 200 / 404             |
| POST   | `/api/libri/salva`                | Create or update a book    | 201 / 200 / 400 / 409 |
| DELETE | `/api/libri/elimina/{id}`         | Delete a book              | 200 / 404             |

### Usage Examples

#### Create a book

```bash
curl -X POST http://localhost:8080/api/libri/salva \
  -H "Content-Type: application/json" \
  -d '{
        "titolo": "Il nome della rosa",
        "autore": "Umberto Eco",
        "isbn": "978-8845292866",
        "categoria": "Romanzo",
        "annoPubblicazione": 1980,
        "prezzo": 15.90
      }'
```

Response `201 Created`:

```json
{
  "id": 1,
  "titolo": "Il nome della rosa",
  "autore": "Umberto Eco",
  "isbn": "978-8845292866",
  "categoria": "Romanzo",
  "annoPubblicazione": 1980,
  "prezzo": 15.90
}
```

#### List all books

```bash
curl http://localhost:8080/api/libri
```

### Error Handling

All errors return the same JSON structure:

```json
{
  "timestamp": "2026-06-17T11:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Libro non trovato con id: 99",
  "path": "/api/libri/99"
}
```

| Situation                        | HTTP Status |
|----------------------------------|-------------|
| Resource not found               | 404         |
| Invalid input (e.g. empty title) | 400         |
| Duplicate ISBN                   | 409         |
| Internal error                   | 500         |

### Testing

Tests use an in-memory **H2** database, so **no PostgreSQL is required** to run them:

```bash
./mvnw test
```

The test suite covers:
- Spring context startup (`LibreriaApplicationTests`)
- Controller tests with `MockMvc`: list, not-found (404) and validation (400)

### Roadmap

Possible future improvements, intentionally left out to keep the project simple:

- Use `BigDecimal` instead of `Double` for the price
- Make updates transactional (`@Transactional`) and add optimistic locking
- Add pagination to `GET /api/libri`
- Make the URLs more RESTful (`POST /api/libri`, `PUT /api/libri/{id}`, `DELETE /api/libri/{id}`)
- Add authentication and a CI pipeline (GitHub Actions)

<p align="right"><a href="#libreria">⬆ back to top</a></p>

---

## Italiano

API REST di backend sviluppata con Java e Spring Boot per gestire un catalogo di libri (creare,
leggere, aggiornare, eliminare e cercare libri). Segue un'architettura a livelli pulita con DTO,
validazione dei dati in ingresso, gestione centralizzata degli errori con i codici HTTP corretti,
PostgreSQL con migrazioni Flyway e documentazione interattiva delle API tramite Swagger. I test
automatici girano su un database H2 in memoria, quindi il build è verde senza alcuna dipendenza esterna.

> Realizzato come progetto di studio per consolidare le basi dello sviluppo backend in Java.

### Indice

- [Funzionalità](#funzionalità)
- [Tecnologie](#tecnologie)
- [Architettura](#architettura)
- [Avvio](#avvio)
- [Endpoint API](#endpoint-api)
- [Esempi di utilizzo](#esempi-di-utilizzo)
- [Gestione degli errori](#gestione-degli-errori)
- [Test](#test)
- [Sviluppi futuri](#sviluppi-futuri)

### Funzionalità

- Operazioni CRUD complete sui libri
- Ricerca per id, titolo, autore o ISBN
- Validazione dei dati in ingresso (campi obbligatori, prezzo non negativo)
- Gestione centralizzata degli errori con risposte JSON coerenti (400, 404, 409, 500)
- Versionamento dello schema del database con Flyway
- Documentazione interattiva delle API con Swagger UI
- Test automatici su database H2 in memoria

### Tecnologie

- **Java 17**
- **Spring Boot 4.1.0** — Spring Web MVC, Spring Data JPA, Bean Validation
- **PostgreSQL** — database principale
- **Flyway** — migrazioni del database
- **H2** — database in memoria per i test
- **Lombok** — riduzione del codice ripetitivo
- **springdoc / Swagger UI** — documentazione delle API
- **JUnit 5 + Mockito + MockMvc** — test
- **Maven** — build tool (wrapper incluso)

### Architettura

Il progetto segue un'architettura a livelli, in cui ogni livello ha una sola responsabilità:

```
Richiesta HTTP
     │
     ▼
 Controller   → gestisce richieste/risposte HTTP    (LibroController)
     │
     ▼
 Service      → logica applicativa                  (LibroService / LibroServiceImpl)
     │
     ▼
 Repository   → accesso ai dati (Spring Data JPA)   (LibroRepository)
     │
     ▼
 PostgreSQL
```

Componenti di supporto:
- **Entity** (`Libro`) — mappa la tabella `libri` del database
- **DTO** (`LibroDTO`) — oggetto scambiato con il client; contiene le regole di validazione ed
  evita di esporre direttamente l'entity
- **Mapper** (`LibroMapper`) — converte tra Entity e DTO
- **GlobalExceptionHandler** — punto unico che trasforma le eccezioni in risposte JSON coerenti

#### Struttura del progetto

```
src/main/java/com/example/libreria
├── controller   → endpoint REST
├── service      → logica applicativa (interfaccia + implementazione)
├── repository   → accesso ai dati
├── entity       → tabelle del database
├── dto          → oggetti lato client + validazione
├── mapper       → conversione Entity <-> DTO
├── error        → gestione delle eccezioni e formato degli errori
└── config       → configurazione Swagger / OpenAPI

src/main/resources
├── application.properties
└── db/migration → script SQL di Flyway
```

### Avvio

#### Requisiti

- Java 17
- Un'istanza PostgreSQL attiva

#### Configurazione

Le credenziali del database sono lette da variabili d'ambiente (mai scritte nel codice):

```bash
export DB_URL=jdbc:postgresql://localhost:5432/libreria
export DB_USERNAME=il_tuo_utente
export DB_PASSWORD=la_tua_password
```

#### Esecuzione

```bash
./mvnw spring-boot:run
```

All'avvio Flyway crea automaticamente la tabella `libri`. L'app gira sulla porta `8080`.

Documentazione API (Swagger UI), con l'app avviata:

```
http://localhost:8080/swagger-ui.html
```

### Endpoint API

Base path: `/api/libri`

| Metodo | Endpoint                          | Descrizione                | Risposte              |
|--------|-----------------------------------|----------------------------|-----------------------|
| GET    | `/api/libri`                      | Elenca tutti i libri       | 200                   |
| GET    | `/api/libri/{id}`                 | Cerca un libro per id      | 200 / 404             |
| GET    | `/api/libri/cerca/titolo?titolo=` | Cerca per titolo           | 200                   |
| GET    | `/api/libri/cerca/autore?autore=` | Cerca per autore           | 200                   |
| GET    | `/api/libri/cerca/isbn?isbn=`     | Cerca per ISBN             | 200 / 404             |
| POST   | `/api/libri/salva`                | Crea o aggiorna un libro   | 201 / 200 / 400 / 409 |
| DELETE | `/api/libri/elimina/{id}`         | Elimina un libro           | 200 / 404             |

### Esempi di utilizzo

#### Creare un libro

```bash
curl -X POST http://localhost:8080/api/libri/salva \
  -H "Content-Type: application/json" \
  -d '{
        "titolo": "Il nome della rosa",
        "autore": "Umberto Eco",
        "isbn": "978-8845292866",
        "categoria": "Romanzo",
        "annoPubblicazione": 1980,
        "prezzo": 15.90
      }'
```

Risposta `201 Created`:

```json
{
  "id": 1,
  "titolo": "Il nome della rosa",
  "autore": "Umberto Eco",
  "isbn": "978-8845292866",
  "categoria": "Romanzo",
  "annoPubblicazione": 1980,
  "prezzo": 15.90
}
```

#### Elencare tutti i libri

```bash
curl http://localhost:8080/api/libri
```

### Gestione degli errori

Tutti gli errori restituiscono la stessa struttura JSON:

```json
{
  "timestamp": "2026-06-17T11:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Libro non trovato con id: 99",
  "path": "/api/libri/99"
}
```

| Situazione                          | Codice HTTP |
|-------------------------------------|-------------|
| Risorsa non trovata                 | 404         |
| Dati non validi (es. titolo vuoto)  | 400         |
| ISBN duplicato                      | 409         |
| Errore interno                      | 500         |

### Test

I test usano un database **H2 in memoria**, quindi **non serve PostgreSQL** per eseguirli:

```bash
./mvnw test
```

La suite di test copre:
- l'avvio del contesto Spring (`LibreriaApplicationTests`)
- test del controller con `MockMvc`: lista, risorsa non trovata (404) e validazione (400)

### Sviluppi futuri

Possibili miglioramenti, lasciati fuori di proposito per mantenere il progetto semplice:

- Usare `BigDecimal` invece di `Double` per il prezzo
- Rendere gli aggiornamenti transazionali (`@Transactional`) e aggiungere l'optimistic locking
- Aggiungere la paginazione a `GET /api/libri`
- Rendere gli URL più RESTful (`POST /api/libri`, `PUT /api/libri/{id}`, `DELETE /api/libri/{id}`)
- Aggiungere autenticazione e una pipeline di CI (GitHub Actions)

<p align="right"><a href="#libreria">⬆ torna su</a></p>
