# Followare

**Integrantes:** Anabela Trinidad Cejas, Maira Farias, Martina Iara García, Micaela Abril Marrone, Lucas Mateo Sacks
**Materia:** Gestión Aplicada al Desarrollo de Software II (3665) — UNLaM — 2° cuatrimestre 2026

**Segmento de la comunidad UNLaM:** El segmento de la comunidad UNLaM son estudiantes regulares de cualquier carrera de grado. Los docentes forman parte del segmento con un rol secundario

## Qué es

Followare es una app Android para estudiantes de grado de la UNLaM que **avisa cuando hay novedades en las materias** (mensajes, materiales, fechas, cambios de aula) y junta en un solo lugar información que hoy está repartida entre MIEL e Intraconsulta: materias en curso, sus archivos y el plan con correlativas.

**Estado:** es un MVP. No está conectado a MIEL ni a Intraconsulta. Los datos son de prueba y las novedades se generan con un simulador dentro de la app.

## Cómo se ejecuta

Requisitos: Android Studio (con JDK 17 o superior) y un celular o emulador con Android 7.0 (API 24) o superior.

1. Abrir la carpeta `src/mvp` en Android Studio y esperar a que sincronice Gradle.
2. Ejecutar la configuración `app` en el celular o emulador.
3. Aceptar el permiso de notificaciones (Android 13 o superior).

Desde la terminal, dentro de `src/mvp`:

```bash
./gradlew test            # pruebas unitarias del repositorio de datos
./gradlew assembleDebug   # genera app/build/outputs/apk/debug/app-debug.apk
```

**Para probar con usuarios:** el botón **"Simular novedad"** dispara una de las 4 novedades de prueba como notificación real del celular. **"Restablecer sesión de prueba"** vuelve todo al estado inicial antes de cada usuario.

## Cómo está organizado

| Carpeta | Qué hay |
|---|---|
| `src/mvp` | Código de la app (Java, vistas XML). `ui/` pantallas, `data/` repositorio, datos de prueba y notificaciones, `models/` clases de datos |
| `docs/brief.md` | Brief del producto, versión 5 (la última) |
| `docs/prompts.md` | Registro de todo el trabajo hecho con IA |
| `docs/arquitectura/` | Diagramas de la arquitectura actual y de la gran visión |
| `docs/diseno/` | Alternativas de diseño y wireframe |
| `docs/evaluacion/` | Evaluación heurística (TP4) |
| `docs/evidencia/` | Encuesta y respuestas del TP2, y registro de la prueba del TP5 |
| `docs/entregas/` | PDF de cada entrega |

## Cómo se continúa

Lo que está simulado y habría que construir, en este orden:

1. **Pedir autorización a la UNLaM** para acceder a MIEL e Intraconsulta. Sin esto no se puede avanzar (ver `docs/brief.md`, sección de restricciones).
2. **Servidor de Followare** (Java + Spring Boot + PostgreSQL) que consulte MIEL e Intraconsulta y detecte novedades.
3. **Avisos push con Firebase**, para que lleguen con la app cerrada.
4. **Descarga real de archivos** y botón "Abrir" (hoy el botón dice "Guardado" pero no guarda nada).
5. **Inicio de sesión** con la cuenta de la UNLaM y cálculo real de correlativas a partir de las notas.

Deuda técnica conocida:

* El código de descarga está repetido en 3 pantallas (`NovedadesAdapter`, `DetailBottomSheetDialog` y `MateriaDetalleFragment`).
* El estado de las materias del plan está escrito a mano en `SeedData`.
* Los datos de prueba son de Ingeniería en Informática.
