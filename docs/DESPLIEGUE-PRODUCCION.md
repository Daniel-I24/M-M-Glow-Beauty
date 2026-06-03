# Despliegue en produccion - M&M Glow Beauty

Guia alineada con la **Vista de Despliegue** del proyecto.

## Arquitectura objetivo

| Componente | Servicio recomendado | Puerto / protocolo |
|------------|---------------------|-------------------|
| Frontend React + Vite | Vercel o Netlify | HTTPS 443 |
| Backend Spring Boot | Render (Docker) | HTTPS 8080 |
| Base de datos | MongoDB Atlas | 27017 (TLS) |
| Notificaciones email | Gmail SMTP o SendGrid | 587 |
| Notificaciones WhatsApp | Twilio | HTTPS API |
| Imagenes (opcional) | Cloudinary o AWS S3 | HTTPS |

## 1. MongoDB Atlas

1. Crear cluster gratuito en https://www.mongodb.com/cloud/atlas
2. Usuario de base de datos con permisos read/write
3. Network Access: permitir `0.0.0.0/0` (o IP de Render)
4. Copiar connection string:
   ```
   MONGO_URI=mongodb+srv://USER:PASS@cluster.mongodb.net/mmglowbeauty
   ```
5. Cargar datos iniciales con `mongosh` apuntando a Atlas:
   ```bash
   mongosh "mongodb+srv://..." < init-data.js
   ```

## 2. Backend en Render

1. Conectar repositorio Git en https://render.com
2. Tipo: **Web Service** con Docker o Native Java
3. Build: `mvn clean package -DskipTests`
4. Start: `java -jar target/mmglowbeauty-*.jar`
5. Variables de entorno (minimas):

   | Variable | Valor |
   |----------|-------|
   | `MONGO_URI` | Connection string Atlas |
   | `JWT_SECRET` | Secreto aleatorio largo |
   | `MAIL_USERNAME` | Email de envio |
   | `MAIL_PASSWORD` | App Password Gmail |
   | `CORS_ALLOWED_ORIGINS` | URL del frontend en Vercel |
   | `NOTIFICACION_CANAL` | `EMAIL` |

6. Health check: `GET /swagger-ui.html` o `/api-docs`

## 3. Frontend en Vercel

1. Importar proyecto React/Vite
2. Variable de entorno:
   ```
   VITE_API_URL=https://tu-api.onrender.com
   ```
3. Dominio HTTPS automatico

## 4. Cloudinary (imagenes de servicios y anuncios)

1. Crear cuenta en https://cloudinary.com
2. Configurar en Render / `.env`:
   ```
   STORAGE_PROVIDER=cloudinary
   CLOUDINARY_CLOUD_NAME=xxx
   CLOUDINARY_API_KEY=xxx
   CLOUDINARY_API_SECRET=xxx
   ```
3. El backend ya expone propiedades; la subida de archivos se integra en el frontend llamando a Cloudinary o a un endpoint futuro `/api/media/upload`.

## 5. Seguridad en produccion

- [ ] Cambiar `JWT_SECRET` (nunca usar el valor por defecto)
- [ ] No commitear `.env`
- [ ] Restringir CORS solo al dominio del frontend
- [ ] Usar HTTPS en todos los servicios
- [ ] Rotar credenciales de Twilio y Gmail periodicamente

## 6. Diagramas de arquitectura

Todos los diagramas estan en `docs/`:

- `VistaDeContexto.drawio.png`
- `Vista Funcional.drawio.png`
- `Vista Logica.drawio.png`
- `Vista de Despliegue .drawio.png`
- `vista-desarrollo.puml` / `VistaDesarrollo.png`

Para regenerar la vista de desarrollo:

```bash
java -jar tools/plantuml.jar -tpng -o docs docs/vista-desarrollo.puml
```
