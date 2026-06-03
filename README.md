# M&M Glow Beauty - Sistema de Gestión Backend

Backend REST API para M&M Glow Beauty, un negocio de cosmetología y estética ubicado en Pasto, Nariño, Colombia.

## 📋 Características

- **Gestión de Usuarios**: Administradoras, Empleadas y Clientes
- **Sistema de Citas**: Reservas con verificación de disponibilidad
- **Catálogo de Servicios**: Servicios, tipos de servicio, combos y promociones
- **Inventario de Productos**: Control de stock con alertas automáticas
- **Pagos y Comprobantes**: Registro manual de pagos
- **Sistema de Calificaciones**: Reseñas de clientes con respuestas
- **Recordatorios Automáticos**: Notificaciones 24h y 1h antes de citas
- **Notificaciones**: Email y WhatsApp mediante Strategy Pattern
- **Reportes**: Ingresos, servicios populares e historial
- **Anuncios**: Banners y minipantallas

## 🛠️ Stack Tecnológico

- **Java 21**
- **Spring Boot 3.2.1**
- **MongoDB** (base de datos NoSQL)
- **Spring Security + JWT** (autenticación stateless)
- **JavaMail** (notificaciones por email)
- **Twilio SDK** (notificaciones por WhatsApp)
- **Swagger/OpenAPI 3** (documentación)
- **Docker** (containerización)
- **Maven** (gestión de dependencias)

## 📁 Estructura del Proyecto

```
com.mmglowbeauty/
├── config/              # Configuraciones (Security, MongoDB, CORS, Swagger)
├── controller/          # Controladores REST
├── dto/                 # Data Transfer Objects
├── exception/           # Excepciones personalizadas y manejador global
├── model/               # Entidades de dominio
│   └── enums/          # Enumeraciones
├── repository/          # Repositorios MongoDB
├── scheduler/           # Tareas programadas (recordatorios)
├── security/            # JWT y autenticación
└── service/            # Lógica de negocio
    └── impl/           # Implementaciones de servicios
```

## 🚀 Inicio Rápido

### Prerrequisitos

- Java 21
- Maven 3.8+
- MongoDB 7.0+ (o Docker)

### Instalación Local

1. **Clonar el repositorio**
```bash
git clone <repository-url>
cd M&MGlowBeauty
```

2. **Configurar variables de entorno**
```bash
cp .env.example .env
# Editar .env con tus credenciales
```

3. **Compilar el proyecto**
```bash
mvn clean install
```

4. **Ejecutar la aplicación**
```bash
mvn spring-boot:run
```

La API estará disponible en: `http://localhost:8080`

### Instalación con Docker

1. **Construir y ejecutar**
```bash
docker-compose up --build
```

2. **Detener**
```bash
docker-compose down
```

## 📚 Documentación de la API

Una vez iniciada la aplicación, accede a la documentación interactiva Swagger:

```
http://localhost:8080/swagger-ui.html
```

## 🔐 Autenticación

El sistema utiliza JWT (JSON Web Tokens) para autenticación stateless.

### Login

**Endpoint**: `POST /api/auth/login`

**Request**:
```json
{
  "correo": "admin@mmglowbeauty.com",
  "contrasena": "password"
}
```

**Response**:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "rol": "ADMINISTRADORA",
  "nombre": "María García"
}
```

### Uso del Token

Incluir el token en el header `Authorization` de todas las peticiones protegidas:

```
Authorization: Bearer <token>
```

## 👥 Roles y Permisos

### ADMINISTRADORA
- Acceso completo a todas las funcionalidades
- Gestión de usuarios, servicios, inventario
- Visualización de reportes
- No se puede auto-registrar (solo creación manual)

### EMPLEADA
- Ver sus propias citas
- Consultar catálogo de servicios

### CLIENTE
- Crear y cancelar sus citas
- Calificar servicios
- Ver promociones y combos

## 📧 Configuración de Notificaciones

### Email (JavaMail)

Configurar en `application.properties` o variables de entorno:

```properties
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
notificacion.canal=EMAIL
```

### WhatsApp (Twilio)

```properties
twilio.account.sid=your-sid
twilio.auth.token=your-token
twilio.from.number=whatsapp:+1234567890
notificacion.canal=WHATSAPP
```

## ⏰ Recordatorios Automáticos

El sistema envía recordatorios automáticos:
- **24 horas antes** de la cita
- **1 hora antes** de la cita

Los recordatorios se procesan cada hora mediante `@Scheduled` (cron: `0 0 * * * *`)

## 🧪 Testing

Ejecutar tests unitarios:

```bash
mvn test
```

## 📊 Endpoints Principales

### Autenticación
- `POST /api/auth/login` - Iniciar sesión

### Usuarios
- `POST /api/usuarios` - Crear usuario (ADMIN)
- `GET /api/usuarios` - Listar usuarios (ADMIN)
- `PUT /api/usuarios/{id}` - Actualizar usuario (ADMIN)
- `DELETE /api/usuarios/{id}` - Eliminar usuario (ADMIN)

### Citas
- `POST /api/citas` - Crear cita (ADMIN, CLIENTE)
- `GET /api/citas` - Listar citas (ADMIN)
- `PUT /api/citas/{id}/confirmar` - Confirmar cita (ADMIN)
- `PUT /api/citas/{id}/cancelar` - Cancelar cita (ADMIN, CLIENTE)

### Servicios
- `POST /api/servicios` - Crear servicio (ADMIN)
- `GET /api/servicios` - Listar servicios (todos)
- `PUT /api/servicios/{id}` - Actualizar servicio (ADMIN)
- `DELETE /api/servicios/{id}` - Eliminar servicio (ADMIN)

### Inventario
- `PUT /api/inventario/{id}/entrada` - Registrar entrada (ADMIN)
- `PUT /api/inventario/{id}/salida` - Registrar salida (ADMIN)
- `GET /api/inventario/stock-bajo` - Productos con stock bajo (ADMIN)

### Pagos
- `POST /api/pagos` - Registrar pago (ADMIN)
- `GET /api/pagos/{id}/comprobante` - Generar comprobante (ADMIN)

### Calificaciones
- `POST /api/calificaciones` - Calificar servicio (CLIENTE)
- `PUT /api/calificaciones/{id}/responder` - Responder calificación (ADMIN)
- `GET /api/calificaciones/promedio/{empleadaId}` - Promedio de empleada

### Reportes
- `GET /api/reportes/ingresos?inicio=2024-01-01&fin=2024-12-31` - Reporte de ingresos (ADMIN)
- `GET /api/reportes/servicios-populares` - Servicios más solicitados (ADMIN)
- `GET /api/reportes/historial-citas?inicio=2024-01-01&fin=2024-12-31` - Historial de citas (ADMIN)

## 🏗️ Principios de Diseño

### SOLID
- **S**: Cada clase tiene una responsabilidad única
- **O**: Sistema abierto para extensión (Strategy Pattern para notificaciones)
- **L**: Herencia Usuario → Cliente/Empleada/Administradora
- **I**: Interfaces específicas por servicio
- **D**: Inyección de dependencias por constructor

### Patrones de Diseño
- **Strategy Pattern**: NotificacionStrategy (Email/WhatsApp)
- **Repository Pattern**: Acceso a datos con MongoRepository
- **DTO Pattern**: Separación entre modelos de dominio y API
- **Builder Pattern**: JWT token generation

## 🔒 Seguridad

- Contraseñas encriptadas con BCrypt
- Autenticación stateless con JWT
- CORS configurado
- Validación de entrada con Bean Validation
- Manejo global de excepciones
- Logging de todas las operaciones críticas

## 📝 Logging

Todos los logs se guardan en:
- Consola (nivel INFO)
- Archivo: `logs/mmglowbeauty.log`

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto es privado y pertenece a M&M Glow Beauty.

## 📞 Contacto

M&M Glow Beauty - Pasto, Nariño, Colombia

---

**Desarrollado con ❤️ para M&M Glow Beauty**
