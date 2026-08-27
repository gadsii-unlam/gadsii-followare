# Brief de Producto

## Versión 1 — TP1 (26/08/2026)

**Qué cambió respecto de la versión anterior y por qué:** esta es la primera versión del brief, por lo que no existe una versión anterior con la cual compararla. Esta versión consolida las definiciones tomadas durante el TP1: el segmento de estudiantes de la UNLaM que actualmente cursan materias, el producto Followare, el problema que busca resolver, sus funcionalidades principales, las integraciones previstas, los grupos de usuarios y los supuestos planteados, incluyendo el supuesto crítico. Se registra esta versión como base para poder documentar y comparar los cambios que surjan durante los siguientes trabajos prácticos.

---

## 1. Segmento elegido

**Segmento:** estudiantes de la UNLaM que se encuentran actualmente cursando materias, independientemente de la carrera a la que pertenezcan.

**Tamaño estimado:** aproximadamente 80.000 estudiantes de la UNLaM.

**Qué los distingue del resto:**

Los estudiantes que actualmente cursan materias necesitan consultar frecuentemente información relacionada con su trayectoria académica y sus cursadas, como materias aprobadas y pendientes, promedio, materias en curso, comisiones, horarios y novedades.

Además, utilizan diferentes sistemas de la universidad para gestionar distintos aspectos de su vida académica. Principalmente utilizan **Intraconsulta** para consultar información relacionada con su carrera y **MIEL** para acceder a contenidos, comunicaciones y novedades de las materias.

**Por qué elegimos este segmento:**

* Es el grupo que experimenta directamente el problema que Followare busca resolver.
* Necesita consultar información académica que actualmente se encuentra distribuida entre diferentes plataformas.
* Realiza un seguimiento continuo tanto de su progreso académico como de las materias que está cursando.
* El equipo tiene acceso a estudiantes reales pertenecientes a este segmento para realizar el relevamiento y posteriormente probar el MVP.

---

## 2. Producto

**Nombre:** Followare.

**Descripción:** Followare es una aplicación asistente para estudiantes de la UNLaM que centraliza información académica proveniente de MIEL e Intraconsulta y permite realizar un seguimiento del progreso de la carrera.

### Problema que resuelve

Los estudiantes deben utilizar diferentes sistemas para consultar información relacionada con su carrera y sus materias. Esto dificulta tener una visión centralizada de su progreso académico y mantenerse al tanto de las novedades de sus cursadas.

Followare busca centralizar esta información y facilitar el seguimiento de las materias en curso mediante notificaciones sobre nuevos mensajes, materiales, avisos y publicaciones.

### Funcionalidades principales

* **Seguimiento del progreso académico:** consulta de materias aprobadas y pendientes, avance de la carrera y promedio.
* **Consulta de oferta académica:** consulta de materias, comisiones, horarios y cátedras disponibles.
* **Consulta de materias en curso:** acceso a novedades, materiales y comunicaciones de las materias.
* **Notificaciones:** avisos sobre nuevos mensajes, materiales y publicaciones de las materias.
* **Centralización de información:** consulta desde una misma aplicación de información proveniente de Intraconsulta y MIEL.

---

## 3. Integraciones

Followare requiere integrarse con **Intraconsulta y MIEL**, ya que ambos sistemas contienen información necesaria para las funcionalidades principales del producto.

**Intraconsulta:** permitiría obtener información relacionada con la trayectoria académica y la oferta de materias, como materias aprobadas, pendientes y comisiones disponibles.

**MIEL:** permitiría obtener información relacionada con las materias que el estudiante está cursando, como mensajes, materiales y publicaciones.

La implementación concreta de estas integraciones dependerá de los mecanismos de acceso que permitan los sistemas institucionales.

---

## 4. Grupos de usuarios

### Usuario primario

**Estudiantes de grado que actualmente se encuentran cursando materias.**

Son los principales usuarios de Followare porque experimentan directamente el problema que el producto busca resolver. Se benefician principalmente de la centralización de información académica, el seguimiento de su carrera y las notificaciones de las materias en curso.

### Usuarios secundarios

**Docentes:** están relacionados con el funcionamiento de las materias y utilizan MIEL para publicar materiales, mensajes, avisos y otras comunicaciones destinadas a los estudiantes.

Los docentes no forman parte del segmento principal seleccionado para este TP, ya que las funcionalidades principales de Followare están orientadas a las necesidades de los estudiantes.

---

## 5. Acceso a usuarios

Se identificaron tres usuarios reales pertenecientes al segmento seleccionado:

* **U1:** estudiante de Kinesiología que actualmente cursa materias. Persona conocida en la facultad. Disponible para el relevamiento del TP2 y para la prueba del MVP del TP5.
* **U2:** estudiante de Trabajo Social que actualmente cursa materias. Persona conocida en la facultad. Disponible para el relevamiento del TP2 y para la prueba del MVP del TP5.
* **U3:** estudiante de Contabilidad que actualmente cursa materias. Persona conocida en la facultad. Disponible para el relevamiento del TP2 y para la prueba del MVP del TP5.

---

## 6. Supuestos

* **Asumimos que los estudiantes tienen dificultades para consultar información académica porque deben utilizar diferentes sistemas de la universidad.**
  **Evidencia:** los usuarios mencionan que deben ingresar a MIEL e Intraconsulta para consultar información diferente.

* **Asumimos que los estudiantes necesitan consultar con frecuencia su progreso académico.**
  **Evidencia:** los usuarios afirman que consultan regularmente sus materias aprobadas, pendientes y promedio.

* **Asumimos que los estudiantes quieren conocer de manera sencilla qué materias les faltan para completar su carrera.**
  **Evidencia:** los usuarios manifiestan interés en conocer rápidamente las materias que tienen pendientes.

* **Asumimos que los estudiantes tienen dificultades para mantenerse al tanto de las novedades publicadas en MIEL.**
  **Evidencia:** los usuarios indican que alguna vez no vieron a tiempo una publicación o aviso de una materia.

* **Asumimos que recibir notificaciones sobre nuevas publicaciones o materiales sería útil para los estudiantes.**
  **Evidencia:** los usuarios consideran útil recibir avisos cuando hay novedades en sus materias.

* **Asumimos que los estudiantes consideran útil tener en un mismo lugar la información de MIEL e Intraconsulta.**
  **Evidencia:** los usuarios prefieren consultar la información académica desde una única aplicación.

* **Asumimos que los estudiantes utilizarían Followare de manera recurrente durante el cuatrimestre.**
  **Evidencia:** los usuarios manifiestan que utilizarían la aplicación varias veces durante la cursada.
8.Supuesto crítico

**Asumimos que los estudiantes consideran un problema tener la información académica distribuida entre diferentes sistemas.**

**Evidencia:** los usuarios manifiestan que les resulta incómodo consultar información en diferentes plataformas y que preferirían tenerla centralizada.
