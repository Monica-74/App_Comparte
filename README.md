⚠️ Aviso: Este proyecto ha sido desarrollado por Mónica Blanco como parte del Proyecto Final de los estudios de Técnico Superior desarrollo Aplicaciones Multiplataforma. 
No está permitido su uso comercial sin autorización expresa.


# App_CompArte
Aplicación móvil que gestiona alquiler de habitaciones exclusivo para personas mayores de 65 años.
CompArte - Aplicación móvil (Android Studio)

Plataforma que conecta a personas mayores de 65 años con inquilinos de la misma edad, para fomentar la convivencia intergenarional, a través de
alquileres compartidos en entornos familiares.

##Ïndice

# CompArte – Aplicación móvil (Android)

![CompArte logo](imagenes/comparte-logo.png)

> Plataforma que conecta a personas mayores de 65 años con inquilinos jóvenes para fomentar la convivencia intergeneracional mediante alquileres asequibles y entornos adaptados.

---

## Índice

1. [Descripción general](#descripción-general)
2. [Arquitectura](#arquitectura)
3. [Estructura de carpetas](#estructura-de-carpetas)
4. [Requisitos](#requisitos)
5. [Instalación y ejecución](#instalación-y-ejecución)
6. [Base de datos SQLite](#base-de-datos-sqlite)
7. [Pruebas](#pruebas)
8. [Autores](#autores)

---

## Descripción general

CompArte es una aplicación Android escrita en **Java + XML** que permite a propietarios mayores de 65 años publicar habitaciones y a inquilinos mayores de 65 años reservarlas. 
Busca combatir la soledad, compartir recursos, abaratar el coste del alquiler al ser compartido y promover relaciones solidarias.

### Funcionalidades clave

* **Registro / Inicio de sesión** para inquilino, propietario y administrador.
* **CRUD de habitaciones** con fotos y descripciones. 
* **Confirmación de reservas** por el propietario e inquilino.
* **Chat interno** entre propietario–inquilino.
* **Panel de administración** para gestionar usuarios, publicidad, contenidos (habitaciones) y reservas.

---

## Arquitectura

El proyecto sigue un **patrón MVC modularizado**:



| Capa                       | Paquetes                              | Descripción                                                               |
|----------------------------| ------------------------------------- |---------------------------------------------------------------------------|
| **Models**                 | `models`, `database`                  | Defino la la estructura de datos que usa la app y acceso a datos (SQLite) |
| **Vista**                  | `activities`, `fragments`, `adapters` | UI y binding de datos(para compilar el código y los recursos de al app.   |
| **Controlador**            | `controllers`                         | Une vista‑modelo, para construir la lógica de presentación.               |
| **Utilidades / Servicios** | `utils`, `services`                   | Helpers, validaciones, lógica de negocio transversal.                     |

> **Nota**: Se ESPECIFICAN A CONTINUACIÓN TOdos los paquetes de la app, que necesitan fututas mejoras y escalar el proyecto.

---

## Estructura de carpetas

```
app/
 ├── src/main/java/com/example/comparte/
 │   ├── activities/      # Activities principal
 │   ├── adapters/        # RecyclerView & List adapters
 │   ├── controllers/     # Controladores (MVC)
 │   ├── database/        # SQLiteOpenHelper y DAOs
 │   ├── fragments/       # UI modular
 │   ├── models/          # Entidades de dominio
 │   ├── services/        # Lógica de negocio (futuro)
 │   └── utils/           # Clases de apoyo
 └── res/                 # Layouts, drawables, values, etc.
```

---

## Requisitos

* **Android Studio Flamingo | 2023.3.1** o superior
* **JDK 17** (instalado con Android Studio)
* **Gradle 8.5** (envía wrapper)
* Emulador Android API 24+.

---


##  Base de datos SQLite

La base se crea al iniciar `DBComparte` (extiende `SQLiteOpenHelper`).

Tablas iniciales:

| Tabla          | Descripción                        |+
| -------------- | ---------------------------------- |
| `usuarios`     | Datos de inquilinos y propietarios |
| `habitaciones` | Información de habitaciones        |
| `reservas`     | Relación usuario–habitación–fechas |
| `mensajes`     | Chat interno                       |

Scripts en `database/schema.sql`.

---

## Pruebas

* **Unitarias**: JUnit 5 + Mockito (mock de repositorios).
* **Instrumentadas**: Espresso para flujos de UI.

Ejecutar todas:


./gradlew test          # Funcionales # Unitarias # No funcionales # Automatización # Regresión # Aceptación
./gradlew connectedAndroidTest   # Instrumentadas


---

## Roadmap

* [ ] Implementar capa Repository y patrones DAO.
* [ ] Integrar Room en lugar de SQLite puro.
* [ ] Añadir sistema de pagos (Stripe API).
* [ ] Notificaciones push (Firebase Cloud Messaging).
* [ ] Modo offline con sincronización.

---

## Autores

| Nombre | Rol |
| ------ |  |
| Nombre | Mónica Blanco Martínez. |
| Tutor A | Tutor Antonio  Ayala Tirado. |
| Mentor B | UX/UI  Antonia María Heruzo Herruzo y Antonio Ayala Tirado.|

---

© 2025 – Proyecto CompArte. 
Todos los derechos reservados.
Este código fuente es solo para revisión.No se permite su uso, copia, modificación ni distribución sin permiso escrito del autor.
