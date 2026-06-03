# Desplegar backend en Render + MongoDB Atlas

## Paso 1: MongoDB Atlas (base de datos en la nube)

1. Entra a https://www.mongodb.com/cloud/atlas y crea un cluster **M0 (gratis)**.
2. **Database Access** → usuario con contraseña (guarda ambos).
3. **Network Access** → Add IP → `0.0.0.0/0` (permite Render; en produccion real restringe IPs).
4. **Connect** → Drivers → copia la URI, ejemplo:
   ```
   mongodb+srv://USUARIO:PASSWORD@cluster0.xxxxx.mongodb.net/mmglowbeauty?retryWrites=true&w=majority
   ```
5. Carga datos iniciales desde tu PC:
   ```powershell
   mongosh "TU_MONGO_URI_ATLAS" --file init-data.js
   ```

## Paso 2: Render (API Spring Boot)

1. https://dashboard.render.com → **New +** → **Web Service**.
2. Conecta el repo: `https://github.com/Daniel-I24/M-M-Glow-Beauty`
3. Configuracion:
   - **Name:** `mmglowbeauty-api`
   - **Runtime:** Docker
   - **Dockerfile path:** `./Dockerfile`
   - **Plan:** Free

4. **Environment Variables** (obligatorias):

   | Variable | Valor |
   |----------|--------|
   | `MONGO_URI` | URI de Atlas del paso 1 |
   | `JWT_SECRET` | Cadena aleatoria larga (32+ caracteres) |
   | `MAIL_USERNAME` | Tu Gmail |
   | `MAIL_PASSWORD` | App Password de Gmail |
   | `CORS_ALLOWED_ORIGINS` | URL de tu frontend o `*` solo para pruebas |
   | `NOTIFICACION_CANAL` | `EMAIL` |

5. **Create Web Service** → espera el build (5–10 min la primera vez).

6. URL final: `https://mmglowbeauty-api.onrender.com` (o el nombre que asignes).

## Paso 3: Verificar despliegue

```powershell
# Documentacion API
Invoke-WebRequest "https://TU-SERVICIO.onrender.com/api-docs"

# Login
Invoke-RestMethod -Uri "https://TU-SERVICIO.onrender.com/api/auth/login" `
  -Method POST -ContentType "application/json" `
  -Body '{"correo":"admin@mmglowbeauty.com","contrasena":"password"}'
```

Swagger: `https://TU-SERVICIO.onrender.com/swagger-ui.html`

## Notas

- En plan **Free**, el servicio se duerme tras inactividad; la primera peticion puede tardar ~50 s.
- **No** subas `.env` a GitHub; solo variables en el panel de Render.
- Los diagramas van en `docs/` (local); esa carpeta no se sube al repo.

## Blueprint alternativo

Si Render detecta `render.yaml` en el repo, puedes usar **New Blueprint** e indicar las mismas variables al desplegar.
