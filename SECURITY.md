# Guía de Seguridad - M&M Glow Beauty

## 🔒 Aspectos de Seguridad Implementados

### 1. Autenticación y Autorización

#### JWT (JSON Web Tokens)
- **Stateless Authentication**: No se almacenan sesiones en el servidor
- **Expiración**: Los tokens expiran después de 24 horas (configurable)
- **Firma Segura**: Tokens firmados con HMAC-SHA256
- **Claims personalizados**: Incluye rol del usuario para autorización

#### Roles y Permisos
```
ADMINISTRADORA:
  - Acceso completo a todas las funcionalidades
  - Gestión de usuarios, servicios, inventario
  - Visualización de reportes financieros
  - No puede auto-registrarse (solo creación manual)

EMPLEADA:
  - Ver sus propias citas
  - Consultar catálogo de servicios
  - Acceso limitado a información sensible

CLIENTE:
  - Crear y gestionar sus propias citas
  - Calificar servicios recibidos
  - Ver promociones y combos públicos
  - Auto-registro permitido
```

### 2. Encriptación de Contraseñas

- **BCrypt**: Algoritmo de hash adaptativo
- **Salt automático**: Cada contraseña tiene un salt único
- **Factor de trabajo**: Configurado para 10 rondas
- **Nunca se devuelven**: Las contraseñas nunca se exponen en respuestas API

```java
// Ejemplo de verificación
BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
boolean matches = encoder.matches(plainPassword, hashedPassword);
```

### 3. Validación de Entrada

#### Bean Validation (Jakarta Validation)
Todos los DTOs incluyen validaciones:

```java
@NotBlank(message = "El correo es obligatorio")
@Email(message = "El correo debe ser válido")
public String correo;

@Min(value = 1, message = "La puntuación mínima es 1")
@Max(value = 5, message = "La puntuación máxima es 5")
public int puntuacion;
```

#### Sanitización
- Entrada de usuario validada antes de procesamiento
- Prevención de inyección SQL (usando MongoRepository)
- Validación de formato de fechas y números

### 4. Manejo de Errores

#### Respuestas de Error Consistentes
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 404,
  "error": "Not Found",
  "mensaje": "Usuario no encontrado con id: abc123",
  "path": "/api/usuarios/abc123"
}
```

#### No exponer información sensible
- Stack traces solo en desarrollo
- Mensajes de error genéricos para producción
- Logging detallado solo en servidor

### 5. CORS (Cross-Origin Resource Sharing)

```java
@Configuration
public class CorsConfig {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // Solo orígenes permitidos configurados
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        // Credentials permitidos para cookies/auth
        configuration.setAllowCredentials(true);
    }
}
```

### 6. HTTPS (Producción)

⚠️ **IMPORTANTE**: En producción, SIEMPRE usar HTTPS

```properties
# application-prod.properties
server.ssl.enabled=true
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=${SSL_PASSWORD}
server.ssl.key-store-type=PKCS12
```

### 7. Rate Limiting (Recomendado para Producción)

Implementar limitación de peticiones para prevenir:
- Ataques de fuerza bruta
- DDoS
- Abuso de API

```xml
<!-- Agregar a pom.xml -->
<dependency>
    <groupId>com.github.vladimir-bukhtoyarov</groupId>
    <artifactId>bucket4j-core</artifactId>
    <version>7.6.0</version>
</dependency>
```

---

## 🚨 Consideraciones de Seguridad Adicionales

### Variables de Entorno Sensibles

**NUNCA commitear al repositorio:**
- Contraseñas de base de datos
- Claves JWT
- Credenciales de email
- API keys de terceros (Twilio)

**Usar archivo .env para desarrollo local:**
```bash
# .env (incluido en .gitignore)
JWT_SECRET=una-clave-muy-larga-y-segura-que-nadie-pueda-adivinar
MAIL_PASSWORD=app-password-generada
TWILIO_AUTH_TOKEN=token-secreto
```

**Para producción, usar gestores de secretos:**
- AWS Secrets Manager
- Azure Key Vault
- HashiCorp Vault
- Variables de entorno del sistema

### Generación de JWT Secret Seguro

```bash
# Generar una clave aleatoria de 256 bits
node -e "console.log(require('crypto').randomBytes(32).toString('hex'))"

# O en Java
SecureRandom random = new SecureRandom();
byte[] bytes = new byte[32];
random.nextBytes(bytes);
String secret = Base64.getEncoder().encodeToString(bytes);
```

### Rotación de Credenciales

**Buenas prácticas:**
1. Rotar JWT secret cada 90 días
2. Cambiar contraseñas de servicios regularmente
3. Revocar tokens en caso de compromiso
4. Implementar blacklist de tokens (Redis)

### Auditoría y Logging

**Lo que se registra:**
✅ Intentos de login (exitosos y fallidos)
✅ Creación, actualización y eliminación de recursos
✅ Cambios en configuraciones sensibles
✅ Errores de autorización
✅ Operaciones administrativas

**Lo que NO se registra:**
❌ Contraseñas en texto plano
❌ Tokens JWT completos
❌ Información de tarjetas de crédito
❌ Datos personales sensibles sin necesidad

```java
// Ejemplo de logging seguro
log.info("Login exitoso para: {}", usuario.getCorreo());
// NO: log.info("Login con password: {}", password);
```

---

## 🛡️ Protección contra Vulnerabilidades Comunes

### 1. SQL/NoSQL Injection
✅ **Protegido**: Uso de MongoRepository con consultas parametrizadas
```java
// Seguro
usuarioRepository.findByCorreo(correo);

// Inseguro (NO usado)
// db.usuarios.find({ correo: correo }); // sin sanitizar
```

### 2. Cross-Site Scripting (XSS)
✅ **Protegido**: 
- Spring Security headers por defecto
- Validación de entrada
- Escapado automático en respuestas JSON

### 3. Cross-Site Request Forgery (CSRF)
✅ **Protegido**: 
- CSRF deshabilitado para API REST stateless
- Autenticación basada en tokens JWT (no cookies de sesión)

### 4. Broken Authentication
✅ **Protegido**:
- BCrypt con salt automático
- JWT con expiración
- Logout mediante expiración de token
- No hay sesiones en servidor

### 5. Sensitive Data Exposure
✅ **Protegido**:
- Contraseñas nunca retornadas en APIs
- HTTPS recomendado en producción
- Logs sin información sensible

### 6. Broken Access Control
✅ **Protegido**:
- `@PreAuthorize` en cada endpoint
- Verificación de roles a nivel de método
- Validación de propiedad de recursos

```java
@PreAuthorize("hasRole('ADMINISTRADORA')")
public ResponseEntity<Void> eliminarUsuario(@PathVariable String id)
```

### 7. Security Misconfiguration
✅ **Protegido**:
- Configuración explícita de seguridad
- Valores por defecto seguros
- Documentación clara de configuraciones

---

## 📋 Checklist de Seguridad para Producción

### Antes del Despliegue

- [ ] Cambiar JWT_SECRET por uno seguro (256+ bits)
- [ ] Configurar HTTPS/SSL
- [ ] Usar contraseñas fuertes para MongoDB
- [ ] Habilitar autenticación en MongoDB
- [ ] Restringir CORS a dominios específicos
- [ ] Configurar firewall (solo puertos necesarios)
- [ ] Habilitar rate limiting
- [ ] Revisar y actualizar dependencias
- [ ] Configurar respaldos automáticos de BD
- [ ] Implementar monitoreo y alertas
- [ ] Documentar procedimientos de emergencia
- [ ] Realizar pruebas de penetración básicas

### Configuración de Producción

```properties
# application-prod.properties

# Server
server.port=8443
server.ssl.enabled=true

# Security
jwt.expiration=3600000  # 1 hora en producción

# Logging
logging.level.com.mmglowbeauty=WARN
logging.file.name=/var/log/mmglowbeauty/app.log

# MongoDB con autenticación
spring.data.mongodb.uri=mongodb://user:pass@mongo-host:27017/mmglowbeauty?authSource=admin

# CORS restrictivo
cors.allowed.origins=https://mmglowbeauty.com

# Desactivar endpoints innecesarios
management.endpoints.enabled-by-default=false
management.endpoint.health.enabled=true
```

### Monitoreo Continuo

**Métricas a vigilar:**
- Intentos de login fallidos
- Peticiones HTTP 4xx/5xx
- Tiempo de respuesta de APIs
- Uso de recursos (CPU, memoria)
- Conexiones activas a BD
- Espacio en disco

**Herramientas recomendadas:**
- Spring Boot Actuator
- Prometheus + Grafana
- ELK Stack (Elasticsearch, Logstash, Kibana)
- Sentry para tracking de errores

---

## 🔐 Gestión de Contraseñas de Usuario

### Política de Contraseñas Recomendada

Implementar en el frontend:
- Mínimo 8 caracteres
- Al menos una mayúscula
- Al menos una minúscula
- Al menos un número
- Al menos un carácter especial

### Reseteo de Contraseña

**Flujo seguro recomendado:**
1. Usuario solicita reseteo
2. Sistema genera token único temporal
3. Envía link por email con token
4. Token expira en 1 hora
5. Usuario establece nueva contraseña
6. Token se invalida

---

## 📞 Reporte de Vulnerabilidades

Si encuentras una vulnerabilidad de seguridad:

1. **NO** publicarla públicamente
2. Enviar reporte privado a: security@mmglowbeauty.com
3. Incluir:
   - Descripción de la vulnerabilidad
   - Pasos para reproducir
   - Impacto potencial
   - Sugerencias de solución (opcional)

---

## 📚 Referencias y Recursos

- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [Spring Security Documentation](https://spring.io/projects/spring-security)
- [JWT Best Practices](https://datatracker.ietf.org/doc/html/rfc8725)
- [MongoDB Security Checklist](https://docs.mongodb.com/manual/administration/security-checklist/)

---

**Última actualización:** Enero 2024

**Mantenido por:** Equipo de Desarrollo M&M Glow Beauty
