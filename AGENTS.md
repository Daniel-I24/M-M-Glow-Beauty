# M&M Glow Beauty — AGENTS.md

## Build & run

- **Build:** `mvn clean install` (Java 21, Maven 3.8+)
- **Run:** `mvn spring-boot:run` — serves on `http://localhost:8080`
- **Docker:** `docker-compose up --build` (app + MongoDB 7)
- **Tests:** `mvn test` (JUnit 5 + Mockito, no integration tests)

## Required setup

1. `cp .env.example .env` — populate MAIL_USERNAME, MAIL_PASSWORD, TWILIO_* vars
2. MongoDB must be running on `localhost:27017` (or via Docker Compose)
3. Seed data: `mongosh < init-data.js` — creates users with password `password` (BCrypt hash)

## API & auth

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Login: `POST /api/auth/login` with `{ "correo", "contrasena" }` → returns JWT
- All endpoints except login, swagger, and `/api/{servicios,anuncios/activos,promociones,combos}` require `Authorization: Bearer <token>`
- Roles: `ADMINISTRADORA`, `EMPLEADA`, `CLIENTE` (enforced via `@EnableMethodSecurity`)
- Passwords hashed with BCrypt, JWT expiration 24h (86400000ms)

## Key structural facts

- **Domain language is Spanish** — models (Usuario, Cita, Servicio), enums (RolUsuario, EstadoCita), error messages, and collection names (`usuarios`, `citas`) are all in Spanish
- **Usuario inheritance** mapped via `_tipo` discriminator: Usuario → Cliente, Empleada, Administradora (all in `usuarios` collection)
- **Package layout:** `config/` (Security, CORS, Mongo, Swagger), `controller/`, `dto/`, `exception/`, `model/enums/`, `repository/`, `scheduler/`, `security/`, `service/impl/`
- **Services added during refactor:** `ReporteService`/`ReporteServiceImpl` (report logic moved out of controller), `NotificacionService`/`NotificacionServiceImpl` (notification logic moved out of controller)
- **Notifications:** Strategy pattern — `NotificacionStrategy` interface with `EmailStrategy` and `WhatsAppStrategy` implementations; channel selected via `notificacion.canal` property
- **Scheduler:** `@Scheduled(cron = "0 0 * * * *")` runs hourly, sends 24h and 1h reminders for `CONFIRMADA` appointments
- **Error handling:** `GlobalExceptionHandler` (`@RestControllerAdvice`) maps exceptions → `ErrorResponse` with HTTP status codes: 404 (RecursoNoEncontrado), 409 (SlotNoDisponible), 403 (AccesoNoAutorizado), 400 (validation), 500 (generic)
- **CORS:** allows `http://localhost:5173` (Vite frontend)
- **Logging:** `logs/mmglowbeauty.log` + console at INFO level

## Testing quirks

- Tests use `@ExtendWith(MockitoExtension.class)` — pure unit tests, no Spring context loading
- Tests at `src/test/java/com/mmglowbeauty/service/` — only 4 test files exist

## Design conventions

- Constructor injection (no `@Autowired` on fields)
- `@Transactional` on service implementations
- JWT stores `rol` as custom claim; extracted via `@AuthenticationPrincipal` or manual parsing
- Appointment slot availability checks 30min buffer before/after each existing confirmed cita
- MongoDB `Producto` tracks stock with `cantidadMinima` alert threshold

## Things to watch

- JWT secret is hardcoded in `application.properties` default — change in production
- `init-data.js` uses the same BCrypt hash (`password`) for all seed users
- No migration tool — MongoDB schema evolves via code
