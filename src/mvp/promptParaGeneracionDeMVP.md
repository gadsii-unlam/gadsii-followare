# Prompt Técnico para la Generación del MVP de Followare (Android Nativo)

Actúa como un Ingeniero de Software Senior especializado en desarrollo Android nativo y Diseño de Experiencia de Usuario (UX/UI). Tu objetivo es construir desde cero el **Producto Mínimo Viable (MVP)** de **Followare**, una aplicación móvil nativa para Android destinada a estudiantes de grado de la Universidad Nacional de La Matanza (UNLaM), alojando la totalidad del proyecto exclusivamente dentro del directorio `Mvp/`.

---

## 1. Contexto y Propósito del Producto

Los estudiantes regulares de la UNLaM deben consultar diariamente plataformas separadas para gestionar su cursada:
- **MIEL**: para acceder a contenidos, bibliografía, mensajes de docentes y avisos de cátedra.
- **Intraconsulta**: para consultar información académica, estado de materias, plan de estudios, correlatividades y fechas de finales.

**Problema principal validado:** Los estudiantes se enteran tarde de información importante (cambios de aula, nuevos materiales publicados, confirmación de fechas de parciales) y sufren fricción al tener la información académica fragmentada en múltiples sistemas.

**Propuesta de valor de Followare:** Centralizar en una sola aplicación móvil la bandeja de novedades de MIEL e Intraconsulta, el acceso rápido a las materias en curso con sus materiales descargables y el mapa de correlatividades del plan de estudios, priorizando la **eficiencia** (mínima cantidad de toques posibles, sin pantallas intermedias innecesarias) y la **satisfacción** (notificaciones push oportunas en el sistema operativo del teléfono con acceso directo al detalle).

---

## 2. Alcance Funcional Estricto (Scope del MVP)

### 2.1. Lo que SÍ debe incluirse y construirse
1. **Bandeja Centralizada de Novedades (Pantalla Principal / Home)**:
   - Lista densa ordenada cronológicamente (más recientes arriba) que unifica avisos de cátedra (MIEL) y avisos institucionales (Intraconsulta).
   - Filtros rápidos mediante chips horizontales (`Todas`, `Mensajes`, `Materiales`, `Fechas`, `Intraconsulta`).
   - Diferenciación visual clara entre novedades **no leídas** (punto indicador azul, fondo destacado, tipografía con mayor peso) y **leídas** (fondo neutro, sin punto).
   - Contador dinámico de novedades sin leer tanto en el subtítulo de la barra superior (`Toolbar`) como en el badge numérico del ícono `Novedades` en la barra de navegación inferior (`BottomNavigationView`).
   - Acción rápida en cada tarjeta para alternar manualmente el estado `Marcar como leída` / `Marcar como no leída`.
   - Acción directa de descarga inline (`Descargar` -> `Descargando...` -> `Guardado`) en aquellas novedades que traigan un archivo PDF adjunto, marcando automáticamente la novedad como leída al completarse la descarga.
2. **Panel de Detalle de Novedad (BottomSheet Superpuesto y Deep Link)**:
   - Al tocar cualquier novedad de la bandeja —o al tocar una notificación push en la barra de estado del celular—, debe abrirse inmediatamente un `BottomSheetDialogFragment` superpuesto (`DetailBottomSheetDialog`) sin navegar a pantallas intermedias.
   - Muestra: etiqueta de materia y comisión, tipo de novedad, fecha/hora, título completo, cuerpo íntegro del comunicado y, si posee archivo adjunto, una tarjeta interactiva con nombre del documento, peso en MB y botón de descarga con feedback visual de progreso y estado persistido (`Guardado`).
   - Abrir el detalle marca automáticamente la novedad como leída y actualiza los contadores globales en tiempo real.
3. **Materias en Curso y Contenidos por Unidad**:
   - Vista dedicada (`MateriasFragment`) que lista las materias que el alumno está cursando actualmente, indicando código, nombre, comisión, docente a cargo, días/horarios de cursada y un badge con la cantidad de novedades sin leer específicas de esa materia.
   - Despliegue tipo acordeón (expandible/colapsable) en cada materia para visualizar sus **Unidades temáticas** y los **Archivos bibliográficos (PDF)** asociados a cada unidad.
   - Posibilidad de descargar cualquier archivo desde la unidad con transición de estados (`Descargar` -> `Descargando...` -> `Guardado`) sincronizada con el resto de la aplicación.
4. **Plan de Estudios y Árbol Bidireccional de Correlatividades**:
   - Vista dedicada (`PlanFragment`) con buscador en tiempo real por nombre o código de materia.
   - Lista de materias del plan indicando código, año, cuatrimestre y un badge de estado académico con codificación cromática semántica:
     - `Aprobada` (verde, mostrando la nota final obtenida, ej. `Aprobada (9)`).
     - `Cursando` (azul institucional).
     - `Habilitada` (cian/turquesa, lista para inscribirse por tener sus correlativas cumplidas).
     - `Bloqueada` (gris/ámbar, indicando explícitamente qué materias correlativas faltan aprobar).
   - **Inspección interactiva de correlatividades (`PlanDetailBottomSheetDialog`)**: al tocar cualquier materia del plan, se despliega un panel inferior detallando bidireccionalmente:
     1. **Materias de las que depende (Correlativas previas requeridas)**: qué materias necesita aprobar el alumno para desbloquear y poder cursar la materia seleccionada, mostrando el estado actual de cada requisito. Si es una materia inicial de 1° cuatrimestre sin requisitos, debe indicarlo explícitamente.
     2. **Materias que dependen de esta (Correlativas posteriores que destraba)**: qué materias futuras del plan se habilitan o desbloquean al aprobar esta materia.
     3. **Navegación recursiva en el grafo**: al tocar cualquiera de las materias listadas dentro de las correlativas previas o posteriores, el panel debe actualizarse dinámicamente para inspeccionar las correlativas de esa nueva materia seleccionada.
5. **Notificaciones Push Nativas en el Sistema Operativo del Celular y Simulador para Pruebas con Usuarios**:
   - Botón de acción flotante (`ExtendedFloatingActionButton` con texto `"Simular novedad"`) que abre un panel de control (`SimulatorBottomSheetDialog`) diseñado para las sesiones de prueba de usabilidad.
   - Permite disparar 4 escenarios realistas de notificación:
     1. *Álgebra I*: Nuevo material disponible (Guía Unidad 2 con PDF adjunto).
     2. *Álgebra I*: Mensaje urgente del profesor por cambio de aula.
     3. *Química General*: Fecha de 1° Parcial confirmada.
     4. *Intraconsulta*: Aviso institucional de apertura de inscripción a finales.
   - Al disparar un escenario:
     - La novedad actualiza su marca de tiempo a `"hoy HH:mm"` (`System.currentTimeMillis()`), pasa a estado **no leída** (`leida = false`), se reordena ubicándose en la **posición #1** de la bandeja de novedades y actualiza los badges de notificaciones pendientes.
     - Se emite una **Notificación Nativa de Android en el Celular** (`NotificationManager` / `NotificationCompat`) utilizando un canal de prioridad alta (`IMPORTANCE_HIGH`) con sonido, vibración, soporte de texto expandible (`BigTextStyle`) y aparición en la barra de estado superior del teléfono (y como alerta emergente *heads-up* del sistema operativo).
     - **Deep Link desde el Sistema Operativo**: al tocar la notificación en la barra del celular (o su botón de acción `"Ver Detalle"`), la aplicación se abre o pasa al frente (`launchMode="singleTop"`) y despliega directamente el `DetailBottomSheetDialog` correspondiente a esa novedad.
   - Incluye un botón **"Restablecer sesión de prueba"** que limpia todos los estados guardados en `SharedPreferences`, restaurando las lecturas y descargas a su estado original para poder iniciar una prueba limpia con el siguiente usuario.

### 2.2. Lo que queda EXCLUIDO del Alcance (Fuera del MVP)
- **No implementar** calculadora ni visualización de promedio general (GPA).
- **No implementar** vistas ni paneles de gestión para el rol docente.
- **No implementar** trámites administrativos generales (solicitud de constancias, certificados, boletos estudiantiles).
- **No implementar** pantallas de onboarding multipaso, tutoriales iniciales ni diálogos de confirmación redundantes que agreguen fricción.
- **No implementar** conexión de red a servidores externos: toda la información proviene de un conjunto de datos semilla (`SeedData`) realista en memoria con persistencia local de estados mediante `SharedPreferences`.

---

## 3. Especificaciones Técnicas y Arquitectura

### 3.1. Stack Tecnológico y Configuración de Gradle
- **Ubicación del proyecto:** Todo el código fuente, recursos y scripts de compilación de Gradle deben residir dentro de la carpeta `Mvp/`.
- **Lenguaje:** Java 11.
- **SDK de Android:**
  - `namespace = "com.unlam.followare"`
  - `applicationId = "com.unlam.followare"`
  - `compileSdk = 34`
  - `minSdk = 24`
  - `targetSdk = 34`
- **Vinculación de vistas:** `ViewBinding` habilitado (`viewBinding = true`). No usar `findViewById`.
- **Dependencias principales:**
  - `androidx.appcompat:appcompat`
  - `com.google.android.material:material` (Material Components 3)
  - `androidx.constraintlayout:constraintlayout`
  - `androidx.recyclerview:recyclerview`
  - `androidx.core:core-ktx`
  - `junit:junit:4.13.2` para pruebas unitarias locales en JVM.

### 3.2. Estructura de Paquetes (`com.unlam.followare`)
Organizar el código bajo una arquitectura limpia basada en el patrón **Repository + Observer** con separación de responsabilidades:

```text
Mvp/
├── app/
│   ├── src/main/
│   │   ├── AndroidManifest.xml
│   │   ├── java/com/unlam/followare/
│   │   │   ├── MainActivity.java
│   │   │   ├── models/
│   │   │   │   ├── Archivo.java
│   │   │   │   ├── EstadoMateria.java
│   │   │   │   ├── Materia.java
│   │   │   │   ├── MateriaPlan.java
│   │   │   │   ├── Novedad.java
│   │   │   │   ├── TipoNovedad.java
│   │   │   │   └── Unidad.java
│   │   │   ├── data/
│   │   │   │   ├── SeedData.java
│   │   │   │   ├── FollowareRepository.java
│   │   │   │   └── FollowareNotificationManager.java
│   │   │   └── ui/
│   │   │       ├── NovedadesFragment.java
│   │   │       ├── NovedadesAdapter.java
│   │   │       ├── DetailBottomSheetDialog.java
│   │   │       ├── MateriasFragment.java
│   │   │       ├── MateriasAdapter.java
│   │   │       ├── PlanFragment.java
│   │   │       ├── PlanAdapter.java
│   │   │       ├── PlanDetailBottomSheetDialog.java
│   │   │       └── SimulatorBottomSheetDialog.java
│   │   └── res/
│   │       ├── drawable/
│   │       ├── layout/
│   │       ├── menu/
│   │       └── values/
│   └── src/test/java/com/unlam/followare/
│       └── FollowareRepositoryTest.java
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
└── gradlew / gradlew.bat
```

---

## 4. Detalle de Modelos de Datos y Lógica de Negocio

### 4.1. Modelos (`com.unlam.followare.models`)
- **`TipoNovedad` (Enum):** `MENSAJE`, `MATERIAL`, `FECHA`, `INSTITUCIONAL`.
- **`EstadoMateria` (Enum):** `APROBADA`, `CURSANDO`, `HABILITADA`, `BLOQUEADA`.
- **`Archivo`:** `id`, `nombre`, `tamanio` (ej. `"1,2 MB"`), `tipo` (`"pdf"`), `descargado` (`boolean`, mutable).
- **`Unidad`:** `numero`, `titulo`, `List<Archivo> archivos`.
- **`Materia`:** `id`, `codigo`, `nombre`, `comision`, `profesor`, `horario`, `List<Unidad> unidades`.
- **`Novedad`:** `id`, `materiaId`, `materiaNombre`, `comision`, `TipoNovedad tipo`, `titulo`, `cuerpo`, `fecha` (mutable), `timestamp` (mutable para reordenamiento al simular), `leida` (`boolean`, mutable), `urgente` (`boolean`), `Archivo archivoAdjunto` (nullable).
- **`MateriaPlan`:** `codigo`, `nombre`, `anio`, `cuatrimestre`, `EstadoMateria estado`, `Integer notaFinal` (nullable), `List<String> correlativasRequeridas` (códigos de materias previas), `List<String> correlativasFaltantes` (descripciones legibles cuando está bloqueada).

### 4.2. Datos Semilla (`SeedData.java`)
- **Materias en curso (3 materias):**
  1. `MAT101` - *Álgebra I* (Comisión 3, Prof. Martínez, Lunes y Miércoles 19:00 - 22:00), con 2 unidades y 3 archivos PDF (`teoria-matrices.pdf`, `ejercicios-unidad-1.pdf` ya descargados; `guia-unidad-2.pdf` pendiente de descarga).
  2. `QUI102` - *Química General* (Comisión 1, Prof. Gómez, Martes 18:00 - 22:00), con 1 unidad y archivo `tabla-periodica-guia.pdf`.
  3. `MAT103` - *Análisis Matemático I* (Comisión 2, Prof. Rossi, Viernes 18:00 - 22:00), con 1 unidad y archivo `teoremas-limites.pdf`.
- **Novedades iniciales (4 novedades variadas):**
  - `nov-alg-material-2` (`MATERIAL`, no leída, urgente, con adjunto `guia-unidad-2.pdf`): *"Nuevo material disponible: Guía Unidad 2"*.
  - `nov-alg-mensaje-1` (`MENSAJE`, no leída, urgente): *"Mensaje del profesor: Cambio de aula"* (Aula 312).
  - `nov-qui-fecha-1` (`FECHA`, leída): *"Fecha de 1° Parcial cargada"* (martes 22 de septiembre 18:30 hs).
  - `nov-intra-finales` (`INSTITUCIONAL`, no leída): *"Inscripción a finales: Turno Noviembre"*.
- **Plan de Estudios (13 materias interconectadas de 1° a 3° año):**
  - `INF101` (Fundamentos de Informática - Aprobada, nota 9, sin correlativas previas).
  - `MAT101` (Álgebra I - Cursando, sin correlativas previas).
  - `QUI102` (Química General - Cursando, sin correlativas previas).
  - `MAT103` (Análisis Matemático I - Aprobada, nota 8, sin correlativas previas).
  - `FIS104` (Física I - Habilitada, requiere `MAT103`).
  - `MAT201` (Análisis Matemático II - Bloqueada, requiere `MAT101` y `MAT103`; falta aprobar Álgebra I).
  - `INF201` (Arquitectura de Computadoras - Habilitada, requiere `INF101`).
  - `INF202` (Algoritmos y Programación - Habilitada, requiere `MAT101` e `INF101`).
  - `FIS201` (Física II - Bloqueada, requiere `FIS104` y `MAT103`).
  - `INF203` (Estructuras de Datos - Bloqueada, requiere `INF202`).
  - `MAT301` (Probabilidad y Estadística - Bloqueada, requiere `MAT201`).
  - `INF301` (Bases de Datos - Bloqueada, requiere `INF202`).
  - `INF303` (Sistemas Operativos - Bloqueada, requiere `INF201` e `INF203`).

### 4.3. Repositorio Singleton (`FollowareRepository.java`)
- Debe ser compatible con pruebas unitarias en JVM aceptando `Context` nulo (si `context == null`, opera puramente en memoria sin invocar `SharedPreferences`).
- Persistencia en `SharedPreferences` (`followare_prefs`) para los conjuntos de IDs de novedades leídas (`read_news_ids`) y archivos descargados (`downloaded_files_ids`).
- Interfaces de observación:
  - `OnDataChangeListener` (`void onDataChanged()`) para actualizar vistas y badges en tiempo real.
  - `OnPushNotificationListener` (`void onPushNotification(Novedad novedad)`) para disparar notificaciones del sistema.
- Métodos clave:
  - `getAllNovedades()` y `getNovedadesByFilter(TipoNovedad filter)` ordenadas descendentemente por `timestamp`.
  - `markNovedadAsRead(String id, boolean read)` y `markArchivoAsDownloaded(String archivoId)` (descargar un archivo adjunto de una novedad también la marca como leída).
  - `getUnreadCount()` y `getUnreadCountByMateria(String materiaId)`.
  - `getPlanEstudios(String query)` con filtrado case-insensitive por nombre o código.
  - `getMateriaPlanByCodigo(String codigo)`.
  - `getMateriasPrevias(MateriaPlan target)`: resuelve los objetos `MateriaPlan` cuyos códigos figuran en `target.getCorrelativasRequeridas()`.
  - `getMateriasPosteriores(MateriaPlan target)`: busca todas las materias del plan que tengan `target.getCodigo()` dentro de su lista `correlativasRequeridas`.
  - `dispatchSimulatedPush(String novedadId)`: actualiza `timestamp = System.currentTimeMillis()`, formatea `fecha = "hoy HH:mm"`, establece `leida = false`, guarda en preferencias, invoca `notifyDataChanged()` y notifica a `pushListeners`.
  - `resetSession()`: borra `SharedPreferences`, recarga `SeedData` y notifica a todos los listeners.

### 4.4. Gestor de Notificaciones Nativas (`FollowareNotificationManager.java`)
- Configura en `AndroidManifest.xml` los permisos `android.permission.POST_NOTIFICATIONS` y `android.permission.VIBRATE`, además de `android:launchMode="singleTop"` en `MainActivity`.
- En `MainActivity`, solicita en tiempo de ejecución el permiso `POST_NOTIFICATIONS` en Android 13+ (API 33+) mediante `ActivityResultContracts.RequestPermission()`.
- Crea el canal `followare_system_channel` con importancia `NotificationManager.IMPORTANCE_HIGH`, vibración y luz azul activadas.
- Construye la notificación con `NotificationCompat.Builder`, estilo `BigTextStyle`, prioridad `PRIORITY_HIGH`, `setAutoCancel(true)` y un `PendingIntent` que apunta a `MainActivity` pasando `EXTRA_NOVEDAD_ID`.
- Cuando el usuario toca la notificación en la barra de estado del celular, `MainActivity` captura el `Intent` en `onCreate` o `onNewIntent`, cambia a la pestaña de Novedades y abre automáticamente `DetailBottomSheetDialog.newInstance(novedadId)`.

---

## 5. Lineamientos de Interfaz Visual (UI) y Paleta de Colores

- **Identidad visual:** Inspirada en un entorno académico limpio, moderno y de alto contraste legible:
  - Azul Primario Institucional: `#0F4C81` (`primary`), `#002D59` (`primary_dark`), `#E7F1FC` (`primary_light`).
  - Acento y Punto de No Leído: `#1A73E8` (`unread_dot`), fondo de tarjeta no leída `#F0F7FF` (`unread_bg`), fondo de tarjeta leída `#FFFFFF` (`read_bg`).
  - Semántica de Estados de Materia:
    - Aprobada: texto `#198754` sobre fondo `#D1E7DD`.
    - Cursando: texto `#0F4C81` sobre fondo `#CFE2FF`.
    - Habilitada: texto `#055160` sobre fondo `#CFF4FC`.
    - Bloqueada: texto `#6C757D` sobre fondo `#F1F3F5`.
- **Navegación principal:**
  - `MaterialToolbar` superior con título `"Followare"` y subtítulo dinámico `"UNLaM • X novedades sin leer"` (o `"UNLaM • Al día"`).
  - `BottomNavigationView` inferior fija con 3 secciones:
    1. `Novedades` (ícono de sobre/avisos + badge de no leídas).
    2. `Materias` (ícono de libro/cursada).
    3. `Plan y Correlativas` (ícono de mapa/red académica).

---

## 6. Pruebas Automatizadas y Verificación de Compilación

1. Crear la suite de tests unitarios en `Mvp/app/src/test/java/com/unlam/followare/FollowareRepositoryTest.java` verificando:
   - Ordenamiento cronológico descendente de `getAllNovedades()`.
   - Filtrado exacto por `TipoNovedad` (`MATERIAL`, `FECHA`, etc.).
   - Actualización de contadores de no leídas al marcar como leída y no leída.
   - Marcado de archivo como descargado y lectura automática de la novedad asociada.
   - Búsqueda en el plan de estudios (13 materias totales) y estados de bloqueo.
   - Consulta bidireccional de correlatividades (`getMateriasPrevias` y `getMateriasPosteriores`) comprobando materias iniciales, intermedias y finales.
   - Despacho de notificación simulada (`dispatchSimulatedPush`), verificando que el listener sea invocado, que la novedad pase a no leída y que escale a la posición `0` de la lista.
2. Verificar que el proyecto compile sin errores y pase todos los tests ejecutando:
   - `./gradlew test`
   - `./gradlew assembleDebug` (generando el instalable en `Mvp/app/build/outputs/apk/debug/app-debug.apk`).

