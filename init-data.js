// Script de inicialización de datos para MongoDB
// Ejecutar con: mongosh < init-data.js

use mmglowbeauty

// Limpiar colecciones existentes
db.usuarios.deleteMany({})
db.tipos_servicio.deleteMany({})
db.servicios.deleteMany({})
db.productos.deleteMany({})
db.anuncios.deleteMany({})
db.promociones.deleteMany({})

print("Base de datos limpiada")

// 1. CREAR ADMINISTRADORA
// Contraseña: admin123
db.usuarios.insertOne({
  "_tipo": "ADMINISTRADORA",
  "_class": "com.mmglowbeauty.model.Administradora",
  "nombre": "María García",
  "correo": "admin@mmglowbeauty.com",
  "telefono": "3001234567",
  "contrasena": "$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi",
  "rol": "ADMINISTRADORA",
  "fechaRegistro": new Date(),
  "codigoAdmin": "ADMIN001"
})

print("✓ Administradora creada: admin@mmglowbeauty.com / password")

// 2. CREAR EMPLEADAS
// Contraseña para todas: empleada123
const empleadas = db.usuarios.insertMany([
  {
    "_tipo": "EMPLEADA",
    "_class": "com.mmglowbeauty.model.Empleada",
    "nombre": "Lucía Fernández",
    "correo": "lucia@mmglowbeauty.com",
    "telefono": "3002345678",
    "contrasena": "$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi",
    "rol": "EMPLEADA",
    "fechaRegistro": new Date(),
    "especialidad": "Manicurista",
    "turno": "Mañana (8:00 - 14:00)",
    "codigoEmpleada": "EMP001"
  },
  {
    "_tipo": "EMPLEADA",
    "_class": "com.mmglowbeauty.model.Empleada",
    "nombre": "Sofía Martínez",
    "correo": "sofia@mmglowbeauty.com",
    "telefono": "3003456789",
    "contrasena": "$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi",
    "rol": "EMPLEADA",
    "fechaRegistro": new Date(),
    "especialidad": "Esteticista Facial",
    "turno": "Tarde (14:00 - 20:00)",
    "codigoEmpleada": "EMP002"
  }
])

print("✓ Empleadas creadas (2)")

// 3. CREAR CLIENTES
// Contraseña para todos: cliente123
const clientes = db.usuarios.insertMany([
  {
    "_tipo": "CLIENTE",
    "_class": "com.mmglowbeauty.model.Cliente",
    "nombre": "Ana Rodríguez",
    "correo": "ana@example.com",
    "telefono": "3104567890",
    "contrasena": "$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi",
    "rol": "CLIENTE",
    "fechaRegistro": new Date(),
    "direccion": "Calle 18 #25-30, Pasto",
    "fechaNacimiento": new Date("1995-05-15"),
    "preferencias": "Prefiere horarios matutinos"
  },
  {
    "_tipo": "CLIENTE",
    "_class": "com.mmglowbeauty.model.Cliente",
    "nombre": "Camila Torres",
    "correo": "camila@example.com",
    "telefono": "3115678901",
    "contrasena": "$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi",
    "rol": "CLIENTE",
    "fechaRegistro": new Date(),
    "direccion": "Carrera 27 #15-45, Pasto",
    "fechaNacimiento": new Date("1992-08-20"),
    "preferencias": "Alérgica a ciertos productos químicos"
  }
])

print("✓ Clientes creados (2)")

// 4. CREAR TIPOS DE SERVICIO
const tiposServicio = db.tipos_servicio.insertMany([
  {
    "nombre": "Manicure y Pedicure",
    "descripcion": "Servicios de cuidado de manos y pies"
  },
  {
    "nombre": "Tratamientos Faciales",
    "descripcion": "Cuidado y embellecimiento facial"
  },
  {
    "nombre": "Depilación",
    "descripcion": "Servicios de depilación con cera"
  },
  {
    "nombre": "Maquillaje",
    "descripcion": "Maquillaje para eventos y ocasiones especiales"
  }
])

print("✓ Tipos de servicio creados (4)")

// Obtener IDs de tipos de servicio
const tipoManicure = db.tipos_servicio.findOne({nombre: "Manicure y Pedicure"})._id
const tipoFacial = db.tipos_servicio.findOne({nombre: "Tratamientos Faciales"})._id
const tipoDepilacion = db.tipos_servicio.findOne({nombre: "Depilación"})._id
const tipoMaquillaje = db.tipos_servicio.findOne({nombre: "Maquillaje"})._id

// 5. CREAR PRODUCTOS
const productos = db.productos.insertMany([
  {
    "nombre": "Esmalte Gel Premium",
    "descripcion": "Esmalte de larga duración, varios colores",
    "cantidad": 50,
    "cantidadMinima": 10,
    "precioUnitario": 25000,
    "proveedor": "Beauty Supplies Co."
  },
  {
    "nombre": "Crema Hidratante Facial",
    "descripcion": "Crema para todo tipo de piel",
    "cantidad": 30,
    "cantidadMinima": 5,
    "precioUnitario": 45000,
    "proveedor": "Dermacare S.A."
  },
  {
    "nombre": "Cera Depilatoria",
    "descripcion": "Cera para depilación profesional",
    "cantidad": 15,
    "cantidadMinima": 5,
    "precioUnitario": 35000,
    "proveedor": "Beauty Supplies Co."
  },
  {
    "nombre": "Base de Maquillaje",
    "descripcion": "Base líquida, varios tonos",
    "cantidad": 20,
    "cantidadMinima": 3,
    "precioUnitario": 55000,
    "proveedor": "Makeup Pro"
  }
])

print("✓ Productos creados (4)")

// Obtener IDs de productos
const prodEsmalte = db.productos.findOne({nombre: "Esmalte Gel Premium"})._id
const prodCrema = db.productos.findOne({nombre: "Crema Hidratante Facial"})._id
const prodCera = db.productos.findOne({nombre: "Cera Depilatoria"})._id
const prodBase = db.productos.findOne({nombre: "Base de Maquillaje"})._id

// 6. CREAR SERVICIOS
const servicios = db.servicios.insertMany([
  {
    "nombre": "Manicure Clásico",
    "descripcion": "Limado, cutícula, esmaltado básico",
    "precio": 25000,
    "duracionMin": 45,
    "imagenUrl": "https://example.com/manicure.jpg",
    "activo": true,
    "tipoServicioId": tipoManicure.toString(),
    "productoIds": [prodEsmalte.toString()]
  },
  {
    "nombre": "Manicure en Gel",
    "descripcion": "Manicure con esmalte gel de larga duración",
    "precio": 40000,
    "duracionMin": 60,
    "imagenUrl": "https://example.com/manicure-gel.jpg",
    "activo": true,
    "tipoServicioId": tipoManicure.toString(),
    "productoIds": [prodEsmalte.toString()]
  },
  {
    "nombre": "Pedicure Spa",
    "descripcion": "Pedicure completo con masaje y exfoliación",
    "precio": 35000,
    "duracionMin": 60,
    "imagenUrl": "https://example.com/pedicure.jpg",
    "activo": true,
    "tipoServicioId": tipoManicure.toString(),
    "productoIds": [prodEsmalte.toString()]
  },
  {
    "nombre": "Limpieza Facial Profunda",
    "descripcion": "Limpieza, extracción, mascarilla y masaje facial",
    "precio": 60000,
    "duracionMin": 90,
    "imagenUrl": "https://example.com/facial.jpg",
    "activo": true,
    "tipoServicioId": tipoFacial.toString(),
    "productoIds": [prodCrema.toString()]
  },
  {
    "nombre": "Depilación Facial",
    "descripcion": "Depilación con cera de cejas, labio superior y mentón",
    "precio": 20000,
    "duracionMin": 30,
    "imagenUrl": "https://example.com/depilacion-facial.jpg",
    "activo": true,
    "tipoServicioId": tipoDepilacion.toString(),
    "productoIds": [prodCera.toString()]
  },
  {
    "nombre": "Maquillaje Social",
    "descripcion": "Maquillaje para eventos sociales",
    "precio": 50000,
    "duracionMin": 60,
    "imagenUrl": "https://example.com/maquillaje.jpg",
    "activo": true,
    "tipoServicioId": tipoMaquillaje.toString(),
    "productoIds": [prodBase.toString()]
  }
])

print("✓ Servicios creados (6)")

// 7. CREAR ANUNCIOS
const anuncios = db.anuncios.insertMany([
  {
    "titulo": "¡Bienvenida a M&M Glow Beauty!",
    "descripcion": "El mejor lugar para tu belleza y cuidado personal",
    "imagenUrl": "https://example.com/banner-bienvenida.jpg",
    "tipo": "BANNER",
    "fechaInicio": new Date("2024-01-01"),
    "fechaFin": new Date("2024-12-31"),
    "activo": true
  },
  {
    "titulo": "Promoción Especial",
    "descripcion": "20% de descuento en tratamientos faciales",
    "imagenUrl": "https://example.com/promo-facial.jpg",
    "tipo": "MINIPANTALLA",
    "fechaInicio": new Date(),
    "fechaFin": new Date(new Date().setDate(new Date().getDate() + 30)),
    "activo": true
  }
])

print("✓ Anuncios creados (2)")

// 8. CREAR PROMOCIONES
const anuncioPromo = db.anuncios.findOne({titulo: "Promoción Especial"})._id

const promociones = db.promociones.insertMany([
  {
    "titulo": "Descuento Facial de Temporada",
    "descripcion": "20% de descuento en todos los tratamientos faciales",
    "descuento": 20.0,
    "fechaInicio": new Date(),
    "fechaFin": new Date(new Date().setDate(new Date().getDate() + 30)),
    "activa": true,
    "anuncioId": anuncioPromo.toString()
  }
])

print("✓ Promociones creadas (1)")

// 9. CREAR COMBO DE EJEMPLO
const servicioManicure = db.servicios.findOne({nombre: "Manicure en Gel"})._id
const servicioPedicure = db.servicios.findOne({nombre: "Pedicure Spa"})._id

const combos = db.combos.insertMany([
  {
    "nombre": "Combo Manos & Pies",
    "descripcion": "Manicure en Gel + Pedicure Spa",
    "precioCombo": 65000,
    "descuento": 13.0,
    "servicioIds": [servicioManicure.toString(), servicioPedicure.toString()],
    "anuncioId": null
  }
])

print("✓ Combos creados (1)")

// RESUMEN
print("\n==========================================")
print("✓ DATOS INICIALES CARGADOS EXITOSAMENTE")
print("==========================================\n")
print("CREDENCIALES DE ACCESO:")
print("------------------------")
print("Administradora:")
print("  Email: admin@mmglowbeauty.com")
print("  Password: password")
print("")
print("Empleada 1:")
print("  Email: lucia@mmglowbeauty.com")
print("  Password: password")
print("")
print("Empleada 2:")
print("  Email: sofia@mmglowbeauty.com")
print("  Password: password")
print("")
print("Cliente 1:")
print("  Email: ana@example.com")
print("  Password: password")
print("")
print("Cliente 2:")
print("  Email: camila@example.com")
print("  Password: password")
print("\n==========================================")
print("Accede a la API en: http://localhost:8080")
print("Documentación Swagger: http://localhost:8080/swagger-ui.html")
print("==========================================\n")
