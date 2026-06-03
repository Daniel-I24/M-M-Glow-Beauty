# Guía de Instalación y Configuración - M&M Glow Beauty

## 📋 Prerequisitos

Antes de comenzar, asegúrate de tener instalado:

1. **Java Development Kit (JDK) 21**
   - Descargar desde: https://adoptium.net/
   - Verificar instalación: `java -version`

2. **Maven 3.8+**
   - Descargar desde: https://maven.apache.org/download.cgi
   - Verificar instalación: `mvn -version`

3. **MongoDB 7.0+**
   - Descargar desde: https://www.mongodb.com/try/download/community
   - O usar Docker (recomendado): `docker pull mongo:7`

4. **Git** (opcional)
   - Descargar desde: https://git-scm.com/downloads

## 🚀 Instalación Paso a Paso

### Opción 1: Instalación Local (Sin Docker)

#### 1. Instalar y Configurar MongoDB

**Windows:**
```cmd
# Descargar e instalar MongoDB Community Edition
# Iniciar MongoDB como servicio o manualmente:
mongod --dbpath C:\data\db
```

**Linux/Mac:**
```bash
# Ubuntu/Debian
sudo apt-get install mongodb-org

# macOS con Homebrew
brew tap mongodb/brew
brew install mongodb-community

# Iniciar MongoDB
sudo systemctl start mongod
```

#### 2. Clonar el Proyecto

```bash
cd C:\Users\ivan2\OneDrive\Documentos\M&MGlowBeauty
```

#### 3. Configurar Variables de Entorno

Copiar el archivo de ejemplo y editarlo:

```bash
copy .env.example .env
```

Editar `.env` con tus credenciales:

```properties
# Gmail (para notificaciones por email)
MAIL_USERNAME=tu-email@gmail.com
MAIL_PASSWORD=tu-app-password

# Para generar App Password en Gmail:
# 1. Ir a https://myaccount.google.com/security
# 2. Activar verificación en 2 pasos
# 3. Ir a "Contraseñas de aplicaciones"
# 4. Generar una nueva contraseña para "Correo"
```

#### 4. Compilar el Proyecto

```bash
mvn clean install
```

#### 5. Ejecutar la Aplicación

```bash
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

---

### Opción 2: Instalación con Docker (Recomendado)

#### 1. Instalar Docker

**Windows:**
- Descargar Docker Desktop: https://www.docker.com/products/docker-desktop

**Linux:**
```bash
sudo apt-get update
sudo apt-get install docker.io docker-compose
sudo systemctl start docker
```

#### 2. Configurar Variables de Entorno

```bash
copy .env.example .env
# Editar .env con tus credenciales
```

#### 3. Compilar el JAR

```bash
mvn clean package -DskipTests
```

#### 4. Construir y Ejecutar con Docker Compose

```bash
docker-compose up --build
```

Para ejecutar en segundo plano:

```bash
docker-compose up -d
```

#### 5. Verificar que los Contenedores Están Corriendo

```bash
docker ps
```

Deberías ver dos contenedores:
- `mmglowbeauty-api` (puerto 8080)
- `mmglowbeauty-mongo` (puerto 27017)

#### 6. Detener los Contenedores

```bash
docker-compose down
```

---

## 🔧 Configuración Inicial

### 1. Crear Usuario Administrador

Para crear el primer usuario administrador, puedes usar MongoDB Compass o el shell de MongoDB:

**Usando MongoDB Compass:**
1. Conectar a `mongodb://localhost:27017`
2. Crear base de datos: `mmglowbeauty`
3. Crear colección: `usuarios`
4. Insertar documento:

```json
{
  "_tipo": "ADMINISTRADORA",
  "nombre": "Administradora Principal",
  "correo": "admin@mmglowbeauty.com",
  "telefono": "3001234567",
  "contrasena": "$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi",
  "rol": "ADMINISTRADORA",
  "fechaRegistro": { "$date": "2024-01-15T10:00:00.000Z" },
  "codigoAdmin": "ADMIN001"
}
```

**Nota:** La contraseña encriptada anterior corresponde a: `password`

**Usando MongoDB Shell:**

```bash
mongosh
```

```javascript
use mmglowbeauty

db.usuarios.insertOne({
  "_tipo": "ADMINISTRADORA",
  "nombre": "Administradora Principal",
  "correo": "admin@mmglowbeauty.com",
  "telefono": "3001234567",
  "contrasena": "$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi",
  "rol": "ADMINISTRADORA",
  "fechaRegistro": new Date(),
  "codigoAdmin": "ADMIN001"
})
```

### 2. Probar el Login

**Usando cURL:**

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d "{\"correo\":\"admin@mmglowbeauty.com\",\"contrasena\":\"password\"}"
```

**Usando Postman:**
1. Crear nueva petición POST
2. URL: `http://localhost:8080/api/auth/login`
3. Body (JSON):
```json
{
  "correo": "admin@mmglowbeauty.com",
  "contrasena": "password"
}
```

Deberías recibir un token JWT:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "rol": "ADMINISTRADORA",
  "nombre": "Administradora Principal"
}
```

---

## 📚 Acceder a la Documentación Swagger

Una vez que la aplicación esté corriendo, accede a:

```
http://localhost:8080/swagger-ui.html
```

Aquí encontrarás la documentación interactiva de todos los endpoints.

---

## 🔑 Uso del Token JWT

Para acceder a endpoints protegidos:

1. Obtener el token mediante login
2. Agregar el header en cada petición:

```
Authorization: Bearer <tu-token-jwt>
```

**Ejemplo en cURL:**

```bash
curl -X GET http://localhost:8080/api/usuarios \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

---

## 🧪 Ejecutar Tests

```bash
# Todos los tests
mvn test

# Tests específicos
mvn test -Dtest=UsuarioServiceImplTest
```

---

## 📊 Ver Logs

**Aplicación local:**
```bash
# Los logs se guardan en:
tail -f logs/mmglowbeauty.log
```

**Docker:**
```bash
# Ver logs en tiempo real
docker-compose logs -f app

# Ver últimas 100 líneas
docker-compose logs --tail=100 app
```

---

## 🔄 Actualizar la Aplicación

```bash
# Detener la aplicación
docker-compose down

# Actualizar código (si usas Git)
git pull

# Recompilar
mvn clean package -DskipTests

# Reiniciar
docker-compose up --build -d
```

---

## 🐛 Solución de Problemas

### Puerto 8080 ya en uso

**Windows:**
```cmd
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

**Linux/Mac:**
```bash
lsof -i :8080
kill -9 <PID>
```

### MongoDB no conecta

1. Verificar que MongoDB está corriendo:
```bash
# Windows
sc query MongoDB

# Linux
sudo systemctl status mongod

# Docker
docker ps | grep mongo
```

2. Verificar URI en `application.properties`

### Error al enviar emails

1. Verificar que usas una App Password de Gmail (no tu contraseña normal)
2. Verificar que la verificación en 2 pasos está activada
3. Verificar variables de entorno en `.env`

---

## 📧 Configuración de Notificaciones

### Email (Gmail)

1. Ir a https://myaccount.google.com/security
2. Activar "Verificación en dos pasos"
3. Ir a "Contraseñas de aplicaciones"
4. Generar una contraseña para "Correo"
5. Usar esa contraseña en `MAIL_PASSWORD`

### WhatsApp (Twilio)

1. Crear cuenta en https://www.twilio.com/
2. Obtener Account SID y Auth Token
3. Configurar WhatsApp Sender
4. Agregar las credenciales en `.env`
5. Cambiar `notificacion.canal=WHATSAPP` en `application.properties`

---

## 🎯 Próximos Pasos

1. ✅ Instalar y configurar el sistema
2. ✅ Crear usuario administrador
3. ✅ Probar login y obtener token
4. ✅ Explorar la documentación Swagger
5. ✅ Crear tipos de servicio
6. ✅ Crear servicios
7. ✅ Crear empleadas
8. ✅ Crear clientes
9. ✅ Gestionar citas

---

## 📞 Soporte

Para problemas o preguntas:
- Email: info@mmglowbeauty.com
- Documentación: README.md

---

¡Listo! Tu sistema M&M Glow Beauty está configurado y funcionando 🎉
