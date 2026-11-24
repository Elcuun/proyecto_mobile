# Sistema de Bitácora Digital de Aprendizaje Mensual 📱📗

Aplicación móvil Android desarrollada en **Kotlin** con **Jetpack Compose**, **Material Design 3** y arquitectura **MVVM**. Permite a **Docentes**, **Estudiantes** y **Apoderados** gestionar, registrar y consultar el progreso académico mensual a través de bitácoras de aprendizaje interactivas sincronizadas con un backend en **Spring Boot**, con soporte para capturas de evidencias (Cámara / Galería), geolocalización GPS y un completo sistema de pruebas unitarias y cobertura.

---

## 🎨 Tema de la Aplicación: Verde con Morado
El sistema utiliza una paleta de colores personalizada basada en **Verde (Primary)** y **Morado (Secondary)**, centralizada en `duoc.cl.ui.theme`:
- **Color.kt**: Tokens de color primarios (`#2E7D32`), secundarios (`#6A1B9A`), contenedores y variantes para temas claro y oscuro.
- **Theme.kt**: Configuración global de `Proyecto_mobileTheme`.

---

## 👥 Cuentas Ficticias de Prueba (Credenciales Demo)

| Rol | Nombre | Correo Electrónico | Contraseña | Curso Asignado |
| :--- | :--- | :--- | :--- | :--- |
| 👨‍🏫 **Docente** | María González | `docente@duoc.cl` | `123456` | Todos los Cursos |
| 👨‍🏫 **Docente** | Carlos Rodríguez | `docente2@duoc.cl` | `123456` | Todos los Cursos |
| 🧑‍🎓 **Estudiante** | Ignacia Soto | `estudiante@duoc.cl` | `123456` | 5.º Básico A |
| 🧑‍🎓 **Estudiante** | Lucas Pérez | `estudiante2@duoc.cl` | `123456` | 8.º Básico A |
| 👨‍👩‍👧 **Apoderado** | Juan Soto | `apoderado@duoc.cl` | `123456` | Consulta (Ignacia Soto) |

---

## 🌟 Funcionalidades Principales

### 1. Panel del Docente
- **Creación y Edición de Bitácoras:** Publicación de bitácoras mensuales por curso y asignatura (`POST /api/logbooks`).
- **Gestión de Estado:** Apertura y cierre de bitácoras, y eliminación de registros.
- **Revisión y Retroalimentación:** Consulta de aportes desde el backend (`GET /api/logbooks/{id}/entries`) con opción para ingresar comentarios cualitativos y marcar como revisado (`PATCH /api/entries/{id}/review`).

### 2. Portal del Estudiante
- **Formulario de Registro de Bitácora:** Envío de contenidos, actividades, aprendizajes y dudas mediante `POST /api/logbooks/{id}/entries`.
- **Evidencias (Cámara / Galería):** Adjuntar fotografías mediante `FileProvider` y selector de imágenes.
- **Geolocalización GPS:** Integración con `FusedLocationProviderClient` para adjuntar coordenadas de latitud y longitud.

### 3. Portal de Apoderados
- **Consulta en Red:** Búsqueda y consulta de las entregas del estudiante directamente en el backend mediante `GET /api/guardian/entries` usando nombre y correo electrónico con prioridad por correo.

---

## 🧪 Pruebas Unitarias y Cobertura (JUnit & Kover)

El proyecto incluye una suite completa de pruebas unitarias implementadas con **JUnit 4** siguiendo el patrón **Arrange-Act-Assert (AAA)** para validar todos los formularios y reglas de negocio.

### Ejecutar Pruebas Unitarias:
```bash
./gradlew test
```

### Generar Reporte de Cobertura de Código (JetBrains Kover):
El proyecto cuenta con **Kover** configurado (`org.jetbrains.kotlinx.kover`) para medir la cobertura del código de lógica y validaciones (`duoc.cl.validaciones.*`).
```bash
./gradlew koverHtmlReport
```
Una vez ejecutado, el reporte HTML interactivo se genera en:
`app/build/reports/kover/html/index.html`

---

## 🛠️ Tecnologías y Arquitectura

- **Lenguaje:** Kotlin 2.2+
- **UI Framework:** Jetpack Compose + Material 3
- **Navegación:** Navigation Compose
- **Arquitectura:** MVVM, StateFlow, Kotlin Coroutines
- **Networking:** Retrofit 2 + Gson + OkHttp Interceptor (Bearer Token Auth)
- **Testing & Cobertura:** JUnit 4 + JetBrains Kover

---

## 🚀 Instalación y Compilación

1. Clonar o abrir el proyecto en **Android Studio**.
2. Sincronizar el proyecto con Gradle.
3. Compilar la aplicación en modo debug:
   ```bash
   ./gradlew assembleDebug
   ```
