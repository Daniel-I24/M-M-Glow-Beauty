# Ejemplos de Uso de la API - M&M Glow Beauty

Este documento contiene ejemplos prácticos de cómo usar cada endpoint de la API.

## 🔐 1. AUTENTICACIÓN

### Login como Administradora
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "correo": "admin@mmglowbeauty.com",
    "contrasena": "password"
  }'
```

**Respuesta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "rol": "ADMINISTRADORA",
  "nombre": "María García"
}
```

**Nota:** Usa este token en el header `Authorization: Bearer <token>` para todas las peticiones siguientes.

---

## 👥 2. GESTIÓN DE USUARIOS

### Crear Cliente
```bash
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "nombre": "Carolina López",
    "correo": "carolina@example.com",
    "telefono": "3201234567",
    "contrasena": "password123",
    "rol": "CLIENTE",
    "direccion": "Calle 20 #15-30",
    "fechaNacimiento": "1998-03-15",
    "preferencias": "Prefiere productos naturales"
  }'
```

### Crear Empleada
```bash
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "nombre": "Diana Suárez",
    "correo": "diana@mmglowbeauty.com",
    "telefono": "3207654321",
    "contrasena": "empleada123",
    "rol": "EMPLEADA",
    "especialidad": "Maquilladora Profesional",
    "turno": "Completo (8:00 - 18:00)",
    "codigoEmpleada": "EMP003"
  }'
```

### Listar Todos los Usuarios
```bash
curl -X GET http://localhost:8080/api/usuarios \
  -H "Authorization: Bearer <token>"
```

---

## 📅 3. GESTIÓN DE CITAS

### Crear Nueva Cita
```bash
curl -X POST http://localhost:8080/api/citas \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "clienteId": "6789...",
    "empleadaId": "1234...",
    "servicioIds": ["5678...", "9012..."],
    "fechaHora": "2024-02-15T10:00:00",
    "notas": "Cliente prefiere horario matutino"
  }'
```

### Confirmar Cita
```bash
curl -X PUT http://localhost:8080/api/citas/{id}/confirmar \
  -H "Authorization: Bearer <token>"
```

### Cancelar Cita
```bash
curl -X PUT http://localhost:8080/api/citas/{id}/cancelar \
  -H "Authorization: Bearer <token>"
```

### Listar Citas por Empleada
```bash
curl -X GET http://localhost:8080/api/citas/empleada/{empleadaId} \
  -H "Authorization: Bearer <token>"
```

---

## 💅 4. SERVICIOS

### Crear Tipo de Servicio
```bash
curl -X POST http://localhost:8080/api/tipos-servicio \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "nombre": "Masajes Relajantes",
    "descripcion": "Masajes terapéuticos y de relajación"
  }'
```

### Crear Servicio
```bash
curl -X POST http://localhost:8080/api/servicios \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "nombre": "Masaje Sueco",
    "descripcion": "Masaje de relajación muscular profunda",
    "precio": 80000,
    "duracionMin": 90,
    "tipoServicioId": "abc123...",
    "imagenUrl": "https://example.com/masaje.jpg",
    "productoIds": ["prod123..."]
  }'
```

### Listar Servicios (Público)
```bash
curl -X GET http://localhost:8080/api/servicios
```

---

## 📦 5. INVENTARIO

### Registrar Entrada de Productos
```bash
curl -X PUT "http://localhost:8080/api/inventario/{productoId}/entrada?cantidad=50" \
  -H "Authorization: Bearer <token>"
```

### Registrar Salida de Productos
```bash
curl -X PUT "http://localhost:8080/api/inventario/{productoId}/salida?cantidad=5" \
  -H "Authorization: Bearer <token>"
```

### Verificar Stock Bajo
```bash
curl -X GET http://localhost:8080/api/inventario/stock-bajo \
  -H "Authorization: Bearer <token>"
```

---

## 💰 6. PAGOS

### Registrar Pago
```bash
curl -X POST http://localhost:8080/api/pagos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "citaId": "cita123...",
    "monto": 75000,
    "metodoPago": "EFECTIVO",
    "comprobante": "Pago completo en efectivo"
  }'
```

### Generar Comprobante
```bash
curl -X GET http://localhost:8080/api/pagos/{pagoId}/comprobante \
  -H "Authorization: Bearer <token>" \
  --output comprobante.txt
```

---

## ⭐ 7. CALIFICACIONES

### Registrar Calificación (Cliente)
```bash
curl -X POST http://localhost:8080/api/calificaciones \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token-cliente>" \
  -d '{
    "citaId": "cita123...",
    "clienteId": "cliente123...",
    "puntuacion": 5,
    "comentario": "Excelente servicio, muy profesional!"
  }'
```

### Responder Calificación (Administradora)
```bash
curl -X PUT http://localhost:8080/api/calificaciones/{id}/responder \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d "Gracias por tu comentario, nos alegra que hayas disfrutado el servicio!"
```

### Calcular Promedio de Empleada
```bash
curl -X GET http://localhost:8080/api/calificaciones/promedio/{empleadaId} \
  -H "Authorization: Bearer <token>"
```

---

## 🎯 8. COMBOS Y PROMOCIONES

### Crear Combo
```bash
curl -X POST http://localhost:8080/api/combos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "nombre": "Combo Belleza Total",
    "descripcion": "Manicure + Pedicure + Facial",
    "precioCombo": 100000,
    "descuento": 15,
    "servicioIds": ["serv1...", "serv2...", "serv3..."],
    "anuncioId": null
  }'
```

### Crear Promoción
```bash
curl -X POST http://localhost:8080/api/promociones \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "titulo": "Mes del Amor",
    "descripcion": "30% descuento en tratamientos de pareja",
    "descuento": 30,
    "fechaInicio": "2024-02-01",
    "fechaFin": "2024-02-29",
    "anuncioId": "anuncio123..."
  }'
```

### Validar Vigencia de Promoción
```bash
curl -X GET http://localhost:8080/api/promociones/{id}/vigencia \
  -H "Authorization: Bearer <token>"
```

---

## 📢 9. ANUNCIOS

### Crear Anuncio
```bash
curl -X POST http://localhost:8080/api/anuncios \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "titulo": "Gran Apertura",
    "descripcion": "Nuevas instalaciones en el centro de Pasto",
    "imagenUrl": "https://example.com/apertura.jpg",
    "tipo": "BANNER",
    "fechaInicio": "2024-03-01",
    "fechaFin": "2024-03-31"
  }'
```

### Listar Anuncios Activos (Público)
```bash
curl -X GET http://localhost:8080/api/anuncios/activos
```

---

## 📊 10. REPORTES

### Reporte de Ingresos
```bash
curl -X GET "http://localhost:8080/api/reportes/ingresos?inicio=2024-01-01&fin=2024-01-31" \
  -H "Authorization: Bearer <token>"
```

**Respuesta:**
```json
{
  "fechaInicio": "2024-01-01",
  "fechaFin": "2024-01-31",
  "totalIngresos": 3250000,
  "cantidadPagos": 45,
  "pagos": [...]
}
```

### Servicios Más Populares
```bash
curl -X GET http://localhost:8080/api/reportes/servicios-populares \
  -H "Authorization: Bearer <token>"
```

**Respuesta:**
```json
[
  {
    "servicioId": "abc123...",
    "cantidad": 85
  },
  {
    "servicioId": "def456...",
    "cantidad": 62
  }
]
```

### Historial de Citas
```bash
curl -X GET "http://localhost:8080/api/reportes/historial-citas?inicio=2024-01-01&fin=2024-01-31" \
  -H "Authorization: Bearer <token>"
```

---

## 📧 11. NOTIFICACIONES

### Enviar Notificación Masiva
```bash
curl -X POST http://localhost:8080/api/notificaciones/masiva \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "mensaje": "¡Aprovecha nuestras promociones de febrero! 20% de descuento en todos los servicios faciales."
  }'
```

**Respuesta:**
```json
{
  "totalClientes": 150,
  "enviados": 148,
  "fallidos": 2,
  "mensaje": "¡Aprovecha nuestras promociones de febrero!..."
}
```

---

## 🔄 12. FLUJO COMPLETO DE EJEMPLO

### Escenario: Cliente reserva y completa una cita

#### Paso 1: Cliente se registra
```bash
POST /api/usuarios
{
  "nombre": "Paula Gómez",
  "correo": "paula@example.com",
  "telefono": "3209876543",
  "contrasena": "paula123",
  "rol": "CLIENTE",
  "direccion": "Carrera 30 #18-25"
}
```

#### Paso 2: Cliente hace login
```bash
POST /api/auth/login
{
  "correo": "paula@example.com",
  "contrasena": "paula123"
}
# Guardar token recibido
```

#### Paso 3: Cliente crea una cita
```bash
POST /api/citas
{
  "clienteId": "{id-paula}",
  "empleadaId": "{id-lucia}",
  "servicioIds": ["{id-manicure-gel}"],
  "fechaHora": "2024-02-20T10:00:00",
  "notas": "Primera vez"
}
```

#### Paso 4: Administradora confirma la cita
```bash
PUT /api/citas/{citaId}/confirmar
# Cliente recibe notificación automática
```

#### Paso 5: Recordatorios automáticos
- 24 horas antes: Sistema envía recordatorio automático
- 1 hora antes: Sistema envía recordatorio automático

#### Paso 6: Administradora registra el pago
```bash
POST /api/pagos
{
  "citaId": "{citaId}",
  "monto": 40000,
  "metodoPago": "EFECTIVO"
}
```

#### Paso 7: Cliente califica el servicio
```bash
POST /api/calificaciones
{
  "citaId": "{citaId}",
  "clienteId": "{id-paula}",
  "puntuacion": 5,
  "comentario": "Excelente atención!"
}
```

#### Paso 8: Administradora responde
```bash
PUT /api/calificaciones/{calificacionId}/responder
"Gracias Paula, esperamos verte pronto!"
```

---

## 🎨 13. POSTMAN COLLECTION

Importa esta colección en Postman para probar todos los endpoints fácilmente.

1. Abrir Postman
2. File → Import
3. Seleccionar archivo: `M&M-Glow-Beauty.postman_collection.json`
4. Configurar variable de entorno `{{baseUrl}}` = `http://localhost:8080`
5. Configurar variable `{{token}}` después del login

---

## 💡 TIPS DE USO

### 1. Autenticación
Siempre guarda el token JWT después del login y úsalo en todas las peticiones protegidas.

### 2. Formato de Fechas
Usa formato ISO 8601: `YYYY-MM-DDTHH:mm:ss`
Ejemplo: `2024-02-15T14:30:00`

### 3. IDs de MongoDB
Los IDs son strings hexadecimales de 24 caracteres.
Ejemplo: `507f1f77bcf86cd799439011`

### 4. Códigos de Estado HTTP
- 200: OK
- 201: Created
- 204: No Content
- 400: Bad Request (error de validación)
- 401: Unauthorized (sin token o token inválido)
- 403: Forbidden (sin permisos)
- 404: Not Found
- 409: Conflict (ej: cita no disponible)
- 500: Internal Server Error

### 5. Paginación
Actualmente no hay paginación implementada. Para grandes volúmenes de datos, considera implementarla en el frontend.

---

## 🐛 ERRORES COMUNES

### Error 401 - Unauthorized
- Verifica que el token esté en el header
- Verifica que el formato sea: `Authorization: Bearer <token>`
- El token expira en 24 horas, haz login nuevamente

### Error 403 - Forbidden
- Tu rol no tiene permisos para este endpoint
- Verifica que estés usando el usuario correcto

### Error 409 - Slot No Disponible
- El horario ya está ocupado
- Verifica disponibilidad con otro horario

### Error 400 - Validación
- Revisa que todos los campos requeridos estén presentes
- Verifica el formato de fechas, emails y teléfonos

---

¡Listo para usar la API de M&M Glow Beauty! 🎉
