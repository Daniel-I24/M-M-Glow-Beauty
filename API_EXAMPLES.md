# Ejemplos de Uso de la API - M&M Glow Beauty

Esta guía contiene ejemplos prácticos de cómo usar cada endpoint de la API.

## 📝 Tabla de Contenidos

1. [Autenticación](#autenticación)
2. [Gestión de Usuarios](#gestión-de-usuarios)
3. [Tipos de Servicio](#tipos-de-servicio)
4. [Servicios](#servicios)
5. [Citas](#citas)
6. [Combos](#combos)
7. [Promociones](#promociones)
8. [Anuncios](#anuncios)
9. [Inventario](#inventario)
10. [Pagos](#pagos)
11. [Calificaciones](#calificaciones)
12. [Reportes](#reportes)
13. [Notificaciones](#notificaciones)

---

## Autenticación

### Login

**Endpoint:** `POST /api/auth/login`

**Request:**
```json
{
  "correo": "admin@mmglowbeauty.com",
  "contrasena": "password"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2wiOiJBRE1JTklTVFJBRE9SQSIsInN1YiI6ImFkbWluQG1tZ2xvd2JlYXV0eS5jb20iLCJpYXQiOjE3MDUzMjAwMDAsImV4cCI6MTcwNTQwNjQwMH0.signature",
  "rol": "ADMINISTRADORA",
  "nombre": "Administradora Principal"
}
```

**cURL:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"correo":"admin@mmglowbeauty.com","contrasena":"password"}'
```

---

## Gestión de Usuarios

**Nota:** Todos los endpoints de usuarios requieren rol ADMINISTRADORA.

### Crear Cliente

**Endpoint:** `POST /api/usuarios`

**Headers:**
```
Authorization: Bearer <token>
Content-Type: application/json
```

**Request:**
```json
{
  "nombre": "María González",
  "correo": "maria@example.com",
  "telefono": "3001234567",
  "contrasena": "cliente123",
  "rol": "CLIENTE",
  "direccion": "Calle 5 #10-20",
  "fechaNacimiento": "1990-05-15",
  "preferencias": "Prefiere citas en la mañana"
}
```

**cURL:**
```bash
curl -X POST http://localhost:8080/api/usuarios \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "María González",
    "correo": "maria@example.com",
    "telefono": "3001234567",
    "contrasena": "cliente123",
    "rol": "CLIENTE",
    "direccion": "Calle 5 #10-20"
  }'
```

### Crear Empleada

**Request:**
```json
{
  "nombre": "Lucía Martínez",
  "correo": "lucia@mmglowbeauty.com",
  "telefono": "3009876543",
  "contrasena": "empleada123",
  "rol": "EMPLEADA",
  "especialidad": "Manicure y Pedicure",
  "turno": "Mañana",
  "codigoEmpleada": "EMP001"
}
```

### Listar Todos los Usuarios

**Endpoint:** `GET /api/usuarios`

**cURL:**
```bash
curl -X GET http://localhost:8080/api/usuarios \
  -H "Authorization: Bearer <token>"
```

### Actualizar Usuario

**Endpoint:** `PUT /api/usuarios/{id}`

**Request:**
```json
{
  "nombre": "María González Actualizado",
  "correo": "maria@example.com",
  "telefono": "3001234567",
  "contrasena": "",
  "rol": "CLIENTE",
  "direccion": "Calle 5 #10-25"
}
```

### Eliminar Usuario

**Endpoint:** `DELETE /api/usuarios/{id}`

**cURL:**
```bash
curl -X DELETE http://localhost:8080/api/usuarios/65a1b2c3d4e5f6g7h8i9j0k1 \
  -H "Authorization: Bearer <token>"
```

---

## Tipos de Servicio

### Crear Tipo de Servicio

**Endpoint:** `POST /api/tipos-servicio`

**Request:**
```json
{
  "nombre": "Tratamientos Faciales",
  "descripcion": "Servicios de cuidado facial"
}
```

**cURL:**
```bash
curl -X POST http://localhost:8080/api/tipos-servicio \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Tratamientos Faciales","descripcion":"Servicios de cuidado facial"}'
```

### Listar Tipos de Servicio

**Endpoint:** `GET /api/tipos-servicio`

**cURL:**
```bash
curl -X GET http://localhost:8080/api/tipos-servicio \
  -H "Authorization: Bearer <token>"
```

---

## Servicios

### Crear Servicio

**Endpoint:** `POST /api/servicios`

**Request:**
```json
{
  "nombre": "Manicure Clásico",
  "descripcion": "Manicure con esmaltado tradicional",
  "precio": 25000,
  "duracionMin": 45,
  "tipoServicioId": "65a1b2c3d4e5f6g7h8i9j0k1",
  "imagenUrl": "https://example.com/manicure.jpg",
  "productoIds": ["prod1", "prod2"]
}
```

**cURL:**
```bash
curl -X POST http://localhost:8080/api/servicios \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Manicure Clásico",
    "descripcion": "Manicure con esmaltado tradicional",
    "precio": 25000,
    "duracionMin": 45,
    "tipoServicioId": "65a1b2c3d4e5f6g7h8i9j0k1"
  }'
```

### Listar Servicios (Público)

**Endpoint:** `GET /api/servicios`

**cURL:**
```bash
curl -X GET http://localhost:8080/api/servicios \
  -H "Authorization: Bearer <token>"
```

### Actualizar Servicio

**Endpoint:** `PUT /api/servicios/{id}`

### Eliminar Servicio

**Endpoint:** `DELETE /api/servicios/{id}`

---

## Citas

### Crear Cita

**Endpoint:** `POST /api/citas`

**Roles permitidos:** ADMINISTRADORA, CLIENTE

**Request:**
```json
{
  "clienteId": "65a1b2c3d4e5f6g7h8i9j0k1",
  "empleadaId": "65a1b2c3d4e5f6g7h8i9j0k2",
  "servicioIds": ["65a1b2c3d4e5f6g7h8i9j0k3", "65a1b2c3d4e5f6g7h8i9j0k4"],
  "fechaHora": "2024-12-20T14:30:00",
  "notas": "Cliente prefiere colores neutros"
}
```

**Response:**
```json
{
  "id": "65a1b2c3d4e5f6g7h8i9j0k5",
  "clienteNombre": "María González",
  "empleadaNombre": "Lucía Martínez",
  "serviciosNombres": ["Manicure Clásico", "Pedicure"],
  "fechaHora": "2024-12-20T14:30:00",
  "estado": "PENDIENTE",
  "duracionMin": 90
}
```

**cURL:**
```bash
curl -X POST http://localhost:8080/api/citas \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "clienteId": "65a1b2c3d4e5f6g7h8i9j0k1",
    "empleadaId": "65a1b2c3d4e5f6g7h8i9j0k2",
    "servicioIds": ["65a1b2c3d4e5f6g7h8i9j0k3"],
    "fechaHora": "2024-12-20T14:30:00",
    "notas": "Primera cita"
  }'
```

### Confirmar Cita

**Endpoint:** `PUT /api/citas/{id}/confirmar`

**Rol permitido:** ADMINISTRADORA

**cURL:**
```bash
curl -X PUT http://localhost:8080/api/citas/65a1b2c3d4e5f6g7h8i9j0k5/confirmar \
  -H "Authorization: Bearer <token>"
```

### Cancelar Cita

**Endpoint:** `PUT /api/citas/{id}/cancelar`

**Roles permitidos:** ADMINISTRADORA, CLIENTE

**cURL:**
```bash
curl -X PUT http://localhost:8080/api/citas/65a1b2c3d4e5f6g7h8i9j0k5/cancelar \
  -H "Authorization: Bearer <token>"
```

### Listar Todas las Citas

**Endpoint:** `GET /api/citas`

**Rol permitido:** ADMINISTRADORA

**cURL:**
```bash
curl -X GET http://localhost:8080/api/citas \
  -H "Authorization: Bearer <token>"
```

### Listar Citas por Empleada

**Endpoint:** `GET /api/citas/empleada/{empleadaId}`

**Roles permitidos:** ADMINISTRADORA, EMPLEADA

**cURL:**
```bash
curl -X GET http://localhost:8080/api/citas/empleada/65a1b2c3d4e5f6g7h8i9j0k2 \
  -H "Authorization: Bearer <token>"
```

---

## Combos

### Crear Combo

**Endpoint:** `POST /api/combos`

**Request:**
```json
{
  "nombre": "Combo Manos y Pies",
  "descripcion": "Manicure + Pedicure con descuento",
  "precioCombo": 60000,
  "descuento": 15,
  "servicioIds": ["servicio1", "servicio2"],
  "anuncioId": "anuncio1"
}
```

**cURL:**
```bash
curl -X POST http://localhost:8080/api/combos \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Combo Manos y Pies",
    "descripcion": "Manicure + Pedicure",
    "precioCombo": 60000,
    "descuento": 15,
    "servicioIds": ["servicio1", "servicio2"]
  }'
```

### Listar Combos

**Endpoint:** `GET /api/combos`

---

## Promociones

### Crear Promoción

**Endpoint:** `POST /api/promociones`

**Request:**
```json
{
  "titulo": "Black Friday 2024",
  "descripcion": "30% de descuento en todos los servicios",
  "descuento": 30,
  "fechaInicio": "2024-11-25",
  "fechaFin": "2024-11-30",
  "anuncioId": "anuncio123"
}
```

**cURL:**
```bash
curl -X POST http://localhost:8080/api/promociones \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Black Friday 2024",
    "descripcion": "30% de descuento",
    "descuento": 30,
    "fechaInicio": "2024-11-25",
    "fechaFin": "2024-11-30"
  }'
```

### Validar Vigencia de Promoción

**Endpoint:** `GET /api/promociones/{id}/vigencia`

**Response:**
```json
true
```

### Listar Promociones

**Endpoint:** `GET /api/promociones`

---

## Anuncios

### Crear Anuncio

**Endpoint:** `POST /api/anuncios`

**Request:**
```json
{
  "titulo": "¡Nuevo Servicio!",
  "descripcion": "Ahora ofrecemos depilación láser",
  "imagenUrl": "https://example.com/banner.jpg",
  "tipo": "BANNER",
  "fechaInicio": "2024-12-01",
  "fechaFin": "2024-12-31"
}
```

### Activar Anuncio

**Endpoint:** `PUT /api/anuncios/{id}/activar`

### Desactivar Anuncio

**Endpoint:** `PUT /api/anuncios/{id}/desactivar`

### Listar Anuncios Activos

**Endpoint:** `GET /api/anuncios/activos`

---

## Inventario

### Registrar Entrada de Producto

**Endpoint:** `PUT /api/inventario/{id}/entrada?cantidad=50`

**cURL:**
```bash
curl -X PUT "http://localhost:8080/api/inventario/prod123/entrada?cantidad=50" \
  -H "Authorization: Bearer <token>"
```

### Registrar Salida de Producto

**Endpoint:** `PUT /api/inventario/{id}/salida?cantidad=10`

**cURL:**
```bash
curl -X PUT "http://localhost:8080/api/inventario/prod123/salida?cantidad=10" \
  -H "Authorization: Bearer <token>"
```

### Verificar Stock Bajo

**Endpoint:** `GET /api/inventario/stock-bajo`

**Response:**
```json
[
  {
    "id": "prod123",
    "nombre": "Esmalte Rojo",
    "cantidad": 5,
    "cantidadMinima": 10,
    "precioUnitario": 15000
  }
]
```

### Listar Productos

**Endpoint:** `GET /api/inventario`

---

## Pagos

### Registrar Pago

**Endpoint:** `POST /api/pagos`

**Request:**
```json
{
  "citaId": "cita123",
  "monto": 85000,
  "metodoPago": "EFECTIVO",
  "comprobante": "Recibo #001234"
}
```

**cURL:**
```bash
curl -X POST http://localhost:8080/api/pagos \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "citaId": "cita123",
    "monto": 85000,
    "metodoPago": "EFECTIVO"
  }'
```

### Generar Comprobante

**Endpoint:** `GET /api/pagos/{id}/comprobante`

**Response:** Archivo de texto con el comprobante

**cURL:**
```bash
curl -X GET http://localhost:8080/api/pagos/pago123/comprobante \
  -H "Authorization: Bearer <token>" \
  -o comprobante.txt
```

### Listar Pagos

**Endpoint:** `GET /api/pagos`

---

## Calificaciones

### Registrar Calificación

**Endpoint:** `POST /api/calificaciones`

**Rol permitido:** CLIENTE

**Request:**
```json
{
  "citaId": "cita123",
  "clienteId": "cliente123",
  "puntuacion": 5,
  "comentario": "Excelente servicio, muy profesional"
}
```

**cURL:**
```bash
curl -X POST http://localhost:8080/api/calificaciones \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "citaId": "cita123",
    "clienteId": "cliente123",
    "puntuacion": 5,
    "comentario": "Excelente servicio"
  }'
```

### Responder Calificación

**Endpoint:** `PUT /api/calificaciones/{id}/responder`

**Rol permitido:** ADMINISTRADORA

**Request:**
```json
"Muchas gracias por tu comentario. Nos alegra que hayas disfrutado tu experiencia."
```

**cURL:**
```bash
curl -X PUT http://localhost:8080/api/calificaciones/cal123/responder \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '"Gracias por tu comentario"'
```

### Calcular Promedio de Empleada

**Endpoint:** `GET /api/calificaciones/promedio/{empleadaId}`

**Response:**
```json
4.8
```

---

## Reportes

### Reporte de Ingresos

**Endpoint:** `GET /api/reportes/ingresos?inicio=2024-01-01&fin=2024-12-31`

**Response:**
```json
{
  "fechaInicio": "2024-01-01",
  "fechaFin": "2024-12-31",
  "totalIngresos": 15750000,
  "cantidadPagos": 350,
  "pagos": [...]
}
```

**cURL:**
```bash
curl -X GET "http://localhost:8080/api/reportes/ingresos?inicio=2024-01-01&fin=2024-12-31" \
  -H "Authorization: Bearer <token>"
```

### Servicios Más Solicitados

**Endpoint:** `GET /api/reportes/servicios-populares`

**Response:**
```json
[
  {
    "servicioId": "servicio1",
    "cantidad": 150
  },
  {
    "servicioId": "servicio2",
    "cantidad": 120
  }
]
```

### Historial de Citas

**Endpoint:** `GET /api/reportes/historial-citas?inicio=2024-01-01&fin=2024-12-31`

---

## Notificaciones

### Enviar Notificación Masiva

**Endpoint:** `POST /api/notificaciones/masiva`

**Rol permitido:** ADMINISTRADORA

**Request:**
```json
{
  "mensaje": "¡Tenemos nuevas promociones! Visítanos y descubre nuestros descuentos especiales."
}
```

**Response:**
```json
{
  "totalClientes": 150,
  "enviados": 148,
  "fallidos": 2,
  "mensaje": "¡Tenemos nuevas promociones!..."
}
```

**cURL:**
```bash
curl -X POST http://localhost:8080/api/notificaciones/masiva \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{"mensaje":"¡Tenemos nuevas promociones!"}'
```

---

## 📌 Notas Importantes

### Formato de Fechas

- **LocalDate:** `YYYY-MM-DD` (ejemplo: `2024-12-25`)
- **LocalDateTime:** `YYYY-MM-DDTHH:mm:ss` (ejemplo: `2024-12-25T14:30:00`)

### Códigos de Estado HTTP

- `200 OK` - Operación exitosa
- `201 Created` - Recurso creado exitosamente
- `204 No Content` - Operación exitosa sin contenido
- `400 Bad Request` - Validación fallida
- `401 Unauthorized` - No autenticado
- `403 Forbidden` - No autorizado (sin permisos)
- `404 Not Found` - Recurso no encontrado
- `409 Conflict` - Conflicto (ej: slot no disponible)
- `500 Internal Server Error` - Error del servidor

### Validaciones Comunes

- **Email:** Debe ser válido
- **Puntuación:** Entre 1 y 5
- **Combo:** Mínimo 2 servicios
- **Teléfono:** Requerido
- **Contraseña:** Requerida al crear usuario

---

¡Usa estos ejemplos para interactuar con la API de M&M Glow Beauty! 🎨💅
