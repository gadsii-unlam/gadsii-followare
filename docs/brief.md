# Brief de Producto

## Versión 2 — TP2 (30/08/2026)

**Qué cambió respecto de la versión anterior y por qué:** Luego de analizar a distintos usuarios mediante la técnica de encuesta, se incorpora el perfil de los usuarios reales y los hallazgos obtenidos durante el relevamiento. Los supuestos que teníamos asumidos fueron confrontados con la evidencia obtenida, e indagar sobre la problemática planteada permitió identificar cuáles son las dificultades que los estudiantes enfrentan en su día a día. Los resultados obtenidos permitieron formular la hipótesis de valor y confirmar que el segmento y la problemática planteada inicialmente se encuentran alineados con las necesidades identificadas durante el relevamiento. Por este motivo, no fue necesario modificar la definición general del producto, sino incorporar y sustentar sus definiciones a partir de la información obtenida de usuarios reales.

---

## 1. Segmento elegido

**Segmento:** son estudiantes regulares de la UNLaM que se encuentran actualmente cursando materias, independientemente de la carrera a la que pertenezcan siendo esta una carrera de grado.

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

* **Supuesto crítico:**
  **Asumimos que los estudiantes consideran un problema tener la información académica distribuida entre diferentes sistemas.**
  **Evidencia:** los usuarios manifiestan que les resulta incómodo consultar información en diferentes plataformas y que preferirían tenerla centralizada.

---

## 7. Perfil del Usuario Real

A partir de las respuestas obtenidas, el usuario primario de Followare está representado por estudiantes de distintas carreras de grado de la UNLaM que actualmente se encuentran cursando materias y utilizan habitualmente herramientas tecnológicas. Los tres usuarios analizados presentan una relación avanzada con la tecnología y utilizan MIEL varias veces durante el día. Esto demuestra que el acceso a una aplicación digital no representa una barrera importante para este grupo.

La encuesta recibió un total de cinco respuestas. Se decidió relevar a más de tres estudiantes con el objetivo de obtener una visión más general del problema que se busca solucionar y conocer diferentes experiencias y necesidades. Para el análisis del grupo de usuarios primario, se seleccionaron tres respuestas correspondientes a estudiantes de Contador Público, Trabajo Social y Kinesiología, identificados como U1, U2 y U3. Las otras dos respuestas no fueron incorporadas al análisis, ya que no correspondían a los perfiles de usuarios seleccionados para este relevamiento.

Sin embargo, existen diferencias según el momento de la carrera. El usuario de Contador Público se encuentra en cuarto año y tiene necesidades relacionadas con la organización y planificación de una trayectoria académica más avanzada, mientras que los usuarios de Trabajo Social y Kinesiología se encuentran en primer año y todavía están familiarizándose con la organización de la carrera.

---

## 8. Necesidades reales

**Centralizar información académica**

Los usuarios consultan información en diferentes sistemas y consideran útil, aunque con distintos matices, contar con un lugar que facilite el acceso a la información universitaria. El usuario de Contador Público considera que una solución unificada podría ayudar a mejorar la organización. El usuario de Kinesiología también considera útil evitar tener que ingresar separadamente a distintas plataformas.

Sin embargo, el usuario de Trabajo Social plantea una diferencia importante: considera que la información administrativa y la académica deberían mantenerse separadas. Este resultado no implica necesariamente que la información deba permanecer en plataformas diferentes, sino que una plataforma unificada podría organizar la información en perfiles o secciones diferenciadas, por ejemplo, un perfil académico para consultar materias, cursadas, notas y progreso, y un perfil administrativo para realizar trámites y consultar información relacionada. De esta manera, Followare podría centralizar el acceso sin mezclar distintos tipos de información.

**Recibir información importante de manera oportuna**

Los tres usuarios manifestaron haber recibido tarde alguna información de MIEL. Los tipos de información que consideran útiles para recibir mediante notificaciones incluyen: mensajes de profesores, nuevos materiales, cambios de aula, cambios en la modalidad de cursada, avisos inesperados, fechas de inscripción, recordatorios de evaluaciones y materiales necesarios para próximas clases. El usuario de Contador Público fue particularmente específico respecto de la necesidad de anticiparse a cambios inesperados, como modificaciones de aulas o modalidad de cursada.

**Conocer información de las materias antes de inscribirse**

Los usuarios utilizan principalmente Intraconsulta para consultar horarios y comisiones. En cuanto a los profesores, ninguno de los tres usuarios manifestó conocer siempre de antemano qué docente estará en cada comisión. Los usuarios de Trabajo Social y Kinesiología indicaron que generalmente se enteran del profesor cuando comienzan las clases.

El usuario de Contador Público como alumno de 4to año, considera muy importante conocer al profesor antes de inscribirse y utiliza recomendaciones de compañeros para conocer quién dicta cada comisión. Esto permite identificar una posible necesidad relacionada con la información previa a la inscripción.

---

## 9. Problemas y frustraciones concretas

**La información está distribuida entre diferentes sistemas**

Los estudiantes deben utilizar MIEL e Intraconsulta para consultar diferentes tipos de información. Esto genera la necesidad de recordar qué información se encuentra en cada plataforma y acceder a ellas por separado.

**Los estudiantes pueden enterarse tarde de información importante**

Los tres usuarios respondieron que alguna vez se enteraron tarde de un mensaje, aviso o material publicado en MIEL. Por lo tanto, el problema de las novedades no consiste simplemente en no tener acceso a la información, sino en no enterarse oportunamente de que existe nueva información.

**No siempre se conoce al profesor antes de elegir una comisión**

Los usuarios de Trabajo Social y Kinesiología indicaron que generalmente conocen al profesor cuando comienzan las clases. Esto contrasta con el usuario de Contador Público, que considera fundamental conocer al profesor antes de inscribirse y actualmente utiliza recomendaciones de otros estudiantes para obtener esa información.

**El cálculo del promedio no es una necesidad prioritaria para todos**

Un resultado interesante es que los tres usuarios no demostraron una necesidad fuerte de calcular constantemente su promedio. El usuario de Contador Público y los usuarios de Trabajo Social y Kinesiología indicaron que no suelen consultar su promedio. Por lo tanto, aunque Followare puede incluir esta funcionalidad, el relevamiento indica que no debería considerarse necesariamente la principal propuesta de valor.

---

## 10. Contexto de uso

Los estudiantes utilizan MIEL principalmente durante el día y varias veces.

  * U1 ingresa entre 1 y 5 veces por día.

  * U2 ingresa aproximadamente 2 o 3 veces por día.

  * U3 ingresa aproximadamente 3 veces por día.

Los principales motivos de ingreso son consultar mensajes o avisos, descargar materiales y consultar fechas o información de las materias. En el caso de U2 también se utiliza para entregar trabajos o actividades.

Por lo tanto, Followare sería utilizado principalmente durante el día y a lo largo de la cursada, especialmente cuando el estudiante necesita verificar si existen novedades, consultar información de una materia o revisar fechas.

El relevamiento también muestra que el uso de MIEL es recurrente, por lo que una funcionalidad de notificaciones podría reducir la necesidad de ingresar únicamente para comprobar si existe alguna novedad.

---

## 11. Hipótesis de valor

Creemos que los estudiantes de la UNLaM que actualmente cursan materias tienen el problema de que deben consultar diferentes plataformas y pueden enterarse tarde de información importante de sus materias.

Nuestra solución es Followare, una aplicación que centraliza y facilita el acceso a información académica de MIEL e Intraconsulta y envía notificaciones sobre novedades relevantes, materiales, mensajes y fechas.

Sabremos que estamos en lo correcto si, durante la prueba del MVP, al menos 2 de los 3 estudiantes indican que Followare les facilita el acceso a la información de sus materias y consideran útiles las notificaciones de novedades. Se establece este criterio tomando como referencia que el relevamiento inicial fue realizado con 3 usuarios.

---

## 12. Estado de supuestos

**1. Asumimos que los estudiantes tienen dificultades para consultar información académica porque deben utilizar diferentes sistemas de la universidad.**
  **Estado:** Confirmado.
  **Evidencia:** Los usuarios utilizan MIEL e Intraconsulta para acceder a diferentes tipos de información. Sin embargo, U2 considera que podría ser mejor mantener separada la información administrativa de la académica.

**2. Asumimos que los estudiantes necesitan consultar con frecuencia su progreso académico.**
  **Estado:** Refutado.
  **Evidencia:** Los tres usuarios indicaron que no suelen consultar su promedio. Esto muestra que el seguimiento del progreso académico no aparece como una necesidad frecuente para estos usuarios.

**3. Asumimos que los estudiantes quieren conocer de manera sencilla qué materias les faltan para completar su carrera.**
  **Estado:** Sin evidencia.
  **Evidencia:** La encuesta no preguntó directamente con qué frecuencia consultan las materias pendientes ni si consideran problemática esta tarea.

**4. Asumimos que los estudiantes tienen dificultades para mantenerse al tanto de las novedades publicadas en MIEL.**
  **Estado:** Confirmado.
  **Evidencia:** Los tres usuarios indicaron que alguna vez se enteraron tarde de un mensaje, aviso o material publicado en MIEL.

**5. Asumimos que recibir notificaciones sobre nuevas publicaciones o materiales sería útil para los estudiantes.**
  **Estado:** Confirmado.
  **Evidencia:** Los usuarios manifestaron interés en recibir notificaciones de mensajes, materiales, cambios, recordatorios y otras novedades.

**6. Asumimos que los estudiantes consideran útil tener en un mismo lugar la información de MIEL e Intraconsulta.**
  **Estado:** Confirmado.
  **Evidencia:** U1 y U3 consideran útil centralizar o facilitar el acceso. Pero, por otro lado, U2 prefiere separar la información administrativa de la académica. (Confirmamos el supuesto ya que 2 de 3 encuestados consideran útil centralizar las plataformas)

**7. Asumimos que los estudiantes utilizarían Followare de manera recurrente durante el cuatrimestre.**
  **Estado:** Confirmado.
  **Evidencia:** Los tres usuarios utilizan MIEL varias veces al día y tienen necesidades recurrentes relacionadas con mensajes, materiales y fechas. Esto respalda un uso frecuente de una solución como Followare.

**Supuesto crítico: Asumimos que los estudiantes consideran un problema tener la información académica distribuida entre diferentes sistemas.**
  **Estado:** Confirmado
  **Evidencia:** Los usuarios utilizan MIEL e Intraconsulta para consultar información diferente y consideran útil facilitar el acceso a ella. Sin embargo, el usuario de Trabajo Social plantea que la información académica y administrativa debería mantenerse diferenciada, por lo que la necesidad parece estar más relacionada con facilitar y organizar el acceso a la información.
