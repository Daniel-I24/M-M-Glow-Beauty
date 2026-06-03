# 🚀 Inicio Rápido - M&M Glow Beauty

## En 5 Minutos con Docker

### 1️⃣ Compilar el proyecto
```bash
mvn clean package -DskipTests
```

### 2️⃣ Iniciar con Docker
```bash
docker-compose up -d
```

### 3️⃣ Cargar datos iniciales
```bash
# Conectar a MongoDB
docker exec -it mmglowbeauty-mongo mongosh

# En el shell de MongoDB, pegar el contenido de init-data.js
# O ejecutar:
mongosh < init-data.js
```

### 4️⃣ Probar login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"correo":"admin@mmglowbeauty.com","contrasena":"password"}'
```

### 5️⃣ Acceder a Swagger
Abrir en navegador: http://localhost:8080/swagger-ui.html

---

## Sin Docker (Local)

### 1️⃣ Iniciar MongoDB
```bash
# Windows
mongod --dbpath C:\data\db

# Linux/Mac
sudo systemctl start mongod
```

### 2️⃣ Ejecutar aplicación
```bash
mvn spring-boot:run
```

### 3️⃣ Cargar datos
```bash
mongosh < init-data.js
```

---

## 📝 Credenciales por Defecto

| Rol | Email | Password |
|-----|-------|----------|
| Administradora | admin@mmglowbeauty.com | password |
| Empleada 1 | lucia@mmglowbeauty.com | password |
| Empleada 2 | sofia@mmglowbeauty.com | password |
| Cliente 1 | ana@example.com | password |
| Cliente 2 | camila@example.com | password |

---

## 🔗 Enlaces Útiles

- **API**: http://localhost:8080
- **Swagger**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/api-docs

---

## ⚡ Comandos Rápidos

### Ver logs
```bash
docker-compose logs -f app
```

### Detener
```bash
docker-compose down
```

### Reiniciar
```bash
docker-compose restart
```

### Ver base de datos
```bash
docker exec -it mmglowbeauty-mongo mongosh
use mmglowbeauty
db.usuarios.find()
```

---

## 🎯 Primera Cita (Ejemplo Completo)

### 1. Login como admin
```bash
TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"correo":"admin@mmglowbeauty.com","contrasena":"password"}' \
  | jq -r '.token')
```

### 2. Obtener IDs necesarios
```bash
# Clientes
curl -s http://localhost:8080/api/usuarios \
  -H "Authorization: Bearer $TOKEN" | jq '.[] | select(.rol=="CLIENTE") | {nombre, id: ._id}'

# Empleadas
curl -s http://localhost:8080/api/usuarios \
  -H "Authorization: Bearer $TOKEN" | jq '.[] | select(.rol=="EMPLEADA") | {nombre, id: ._id}'

# Servicios
curl -s http://localhost:8080/api/servicios | jq '.[] | {nombre, id: ._id}'
```

### 3. Crear cita
```bash
curl -X POST http://localhost:8080/api/citas \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "clienteId": "<cliente-id>",
    "empleadaId": "<empleada-id>",
    "servicioIds": ["<servicio-id>"],
    "fechaHora": "2024-02-20T10:00:00",
    "notas": "Primera cita"
  }'
```

### 4. Confirmar cita
```bash
curl -X PUT http://localhost:8080/api/citas/<cita-id>/confirmar \
  -H "Authorization: Bearer $TOKEN"
```

---

## 📚 Más Información

- **Documentación completa**: README.md
- **Guía de instalación**: INSTALL.md
- **Ejemplos de API**: API-EXAMPLES.md
- **Resumen del proyecto**: PROJECT-SUMMARY.md

---

¡Listo para empezar! 🎉
