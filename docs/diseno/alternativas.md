# Parte 2 — Cómo se ve

Documento correspondiente a los puntos 6 y 7 del TP3. Las tres alternativas resuelven el
**mismo flujo principal** definido en el brief v3 (§13): el usuario recibe una notificación,
entra a la aplicación, ve el contenido de esa novedad y termina en sus materias en curso.
Lo que cambia entre las tres no es el contenido sino la **estructura**: cuántas pantallas hay,
qué información entra en cada una y cuánto acompañamiento recibe el usuario.

Alcance de las tres: Notificaciones (MIEL + Intraconsulta), Materias en curso + Contenidos y
Correlatividades. Nada fuera del scope del MVP.

---

## 6. Tres alternativas de diseño

### Alternativa A — "Paso a paso guiado"

**Atributo que privilegia:** Facilidad de aprendizaje.

**Idea estructural:** navegación jerárquica profunda, un solo objetivo por pantalla y textos
que explican qué es cada cosa. Más pasos, pero cada paso es evidente. La notificación siempre
deja al usuario en el Home, nunca en el medio de la aplicación.

**Esquema de pantallas**

```
[1] Notificación push                 [2] HOME (menú de tarjetas)
+---------------------------+         +-----------------------------------+
| Followare                 |         | Hola, Ana                         |
| Álgebra: nuevo material   |         | +-------------------------------+ |
| Toca para verlo           |         | | NOVEDADES                (3)  | |
+---------------------------+         | | Mensajes, materiales y fechas | |
             |                        | | de tus materias               | |
             v                        | +-------------------------------+ |
        (abre el Home)                | +-------------------------------+ |
                                      | | MIS MATERIAS                  | |
                                      | | Lo que estás cursando ahora   | |
                                      | +-------------------------------+ |
                                      | +-------------------------------+ |
                                      | | CORRELATIVIDADES              | |
                                      | | Qué podés cursar y qué falta  | |
                                      | +-------------------------------+ |
                                      +-----------------------------------+
                                                     |
                                                     v
[3] LISTA DE NOVEDADES                [4] DETALLE DE LA NOVEDAD
+-----------------------------+       +-----------------------------------+
| < Home    Novedades         |       | < Novedades > Álgebra             |
|-----------------------------|       |-----------------------------------|
| * Álgebra                   |       | NUEVO MATERIAL                    |
|   Nuevo material - hoy      | --->  | Álgebra I - Comisión 3            |
|-----------------------------|       | Publicado hoy, 14:20              |
| * Química                   |       |                                   |
|   Mensaje del profesor      |       | "Guía de ejercicios Unidad 2"     |
|-----------------------------|       |                                   |
| * Intraconsulta             |       | [    Ver el material    ]         |
|   Inscripción a finales     |       |                                   |
+-----------------------------+       +-----------------------------------+
                                                     |
                                                     v
                                      [5] MATERIAL / DESCARGA
                                      +-----------------------------------+
                                      | < Volver a la novedad             |
                                      | guia-unidad-2.pdf     (1,2 MB)    |
                                      | [      Descargar      ]           |
                                      | Se guardó en tu teléfono          |
                                      +-----------------------------------+

[6] MIS MATERIAS                      [7] DETALLE DE MATERIA
+-----------------------------+       +-----------------------------------+
| < Home    Mis materias      |       | < Mis materias                    |
| - Álgebra I      Com. 3     | --->  | Álgebra I - Comisión 3            |
| - Química        Com. 1     |       | Contenidos                        |
| - Análisis Mat.  Com. 2     |       |  - Unidad 1 (2 archivos)          |
+-----------------------------+       |  - Unidad 2 (1 archivo)           |
                                      | Novedades de esta materia         |
                                      +-----------------------------------+
```

**Decisiones características:** onboarding de 3 pantallas la primera vez; breadcrumb visible;
una sola acción principal por pantalla; el Home es siempre el punto de retorno.

---

### Alternativa B — "Bandeja única densa"

**Atributo que privilegia:** Eficiencia.

**Idea estructural:** el Home **es** la bandeja de novedades. La notificación abre directamente
el contenido (deep link), sin pasar por el menú. El detalle no es una pantalla nueva sino un
panel que se despliega sobre la misma lista, así el usuario no pierde el contexto ni tiene que
volver. Máximo dos niveles de profundidad y acciones inline (descargar, marcar leída) sin abrir
nada. Barra inferior fija de tres secciones.

**Esquema de pantallas**

```
[1] Notificación push  ---- deep link ---->  [2] HOME con el panel ya abierto
+----------------------------+
| Álgebra I - Nuevo material |
| "Guía Unidad 2"            |
| [Descargar]   [Ver]        |    <- acciones desde la propia notificación
+----------------------------+

[2] HOME = BANDEJA DE NOVEDADES (pantalla principal)
+--------------------------------------------------------+
| Followare                              [Buscar]   [cfg]|
| (Todas)(Mensajes)(Material)(Fechas)(Intraconsulta)     |  <- filtros rápidos
|--------------------------------------------------------|
| ÁLGEBRA I - Com. 3                                 (2) |
|  * Nuevo material - "Guía Unidad 2"      hoy 14:20     |
|    [Descargar]  [Abrir]                                |  <- acción sin salir
|  * Mensaje del profesor - "Cambio de aula"  hoy 09:10  |
|--------------------------------------------------------|
| QUÍMICA - Com. 1                                   (1) |
|  * Fecha de parcial cargada - 22/09      ayer          |
|--------------------------------------------------------|
| INTRACONSULTA                                      (1) |
|  * Inscripción a finales: abre 15/09     ayer          |
|--------------------------------------------------------|
| [ Novedades ]    [ Materias ]    [ Plan ]              |  <- barra fija
+--------------------------------------------------------+
                      |
                      v  (tap en una novedad: se expande en el lugar)
[3] PANEL DE DETALLE (sobre la misma lista, no reemplaza la pantalla)
+--------------------------------------------------------+
| ÁLGEBRA I - Com. 3 - Nuevo material              [X]   |
| "Guía de ejercicios Unidad 2" - hoy 14:20              |
| guia-unidad-2.pdf (1,2 MB)      [Descargar]            |
| Ir a la materia >                                      |
+--------------------------------------------------------+

[4] MATERIAS EN CURSO (2da sección)   [5] PLAN / CORRELATIVIDADES (3ra sección)
+-----------------------------+       +-----------------------------------+
| [Novedades][Materias][Plan] |       | [Novedades][Materias][Plan]       |
|                             |       | Buscar materia...                 |
| Álgebra I    Com.3      (2) |       | (v) Análisis Mat. I    aprobada   |
|   Unidad 1 - 2 archivos     |       | (o) Álgebra I          cursando   |
|   Unidad 2 - 1 archivo      |       | ( ) Física I           habilitada |
| Química      Com.1      (1) |       | (x) Análisis Mat. II   falta Álg. |
| Análisis M.  Com.2      ( ) |       +-----------------------------------+
+-----------------------------+
```

**Decisiones características:** la novedad se resuelve desde la notificación o desde la lista,
sin navegar; una sola pantalla concentra toda la información del día; el plan de estudios se
consulta con estados en vez de leer un PDF descargado.

---

### Alternativa C — "Estado del día, con control y secciones separadas"

**Atributo que privilegia:** Satisfacción (sensación de control y de estar al día).

**Idea estructural:** el Home no es una lista sino un **estado**: le dice al usuario si está al
día o qué le falta ver. Separa explícitamente lo académico (materias y contenidos) de lo
institucional/administrativo (Intraconsulta), en dos secciones distintas. Todo es reversible y
confirmado: leído/no leído, "marcar como no leída", preferencias de qué se notifica.

**Esquema de pantallas**

```
[1] Notificación push                 [2] HOME = ESTADO DEL DÍA
+---------------------------+         +-----------------------------------+
| Álgebra I                 |         | Miércoles 9                       |
| Nuevo material            |         |                                   |
+---------------------------+         |   Tenés 3 novedades sin ver       |
             |                        |   (ninguna vencida)               |
             v                        |-----------------------------------|
                                      | ACADÉMICO                     (2) |
                                      |  Materias, materiales, mensajes   |
                                      |  [ Ver novedades académicas ]     |
                                      |-----------------------------------|
                                      | INSTITUCIONAL                 (1) |
                                      |  Inscripciones, plan, trámites    |
                                      |  [ Ver novedades institucionales ]|
                                      |-----------------------------------|
                                      | Mis materias en curso >           |
                                      | Preferencias de notificación >    |
                                      +-----------------------------------+
                                                     |
                                                     v
[3] NOVEDADES DE LA SECCIÓN           [4] DETALLE CON CONFIRMACIÓN
+-----------------------------+       +-----------------------------------+
| < Estado    Académico       |       | < Académico                       |
| SIN VER                     |       | Álgebra I - Nuevo material        |
|  * Álgebra - material  hoy  | --->  | "Guía Unidad 2"                   |
|  * Química - mensaje   hoy  |       | guia-unidad-2.pdf (1,2 MB)        |
| YA VISTAS                   |       | [ Descargar ]                     |
|  - Álgebra - aviso   lunes  |       |   ¿Descargar 1,2 MB? [Sí] [No]    |
|    [Marcar como no leída]   |       |   Guardado. [Deshacer]            |
+-----------------------------+       +-----------------------------------+

[5] MIS MATERIAS                      [6] PREFERENCIAS DE NOTIFICACIÓN
+------------------------------+      +-----------------------------------+
| Álgebra I  Com.3    al día   |      | Avisarme de:                      |
| Química    Com.1  1 sin ver  |      |  [x] Mensajes de profesores       |
| Análisis M.Com.2    al día   |      |  [x] Nuevos materiales            |
+------------------------------+      |  [x] Fechas de parcial            |
                                      |  [ ] Novedades institucionales     |
                                      | Por materia: Álgebra [x] Quím [x] |
                                      +-----------------------------------+
```

**Decisiones características:** estado explícito "al día / sin ver"; separación académico vs.
institucional en el primer nivel; confirmación antes de descargar y "deshacer" después; el
usuario decide qué se le notifica.

---

## 7. Elegir y fundamentar

| Alternativa | Atributo que privilegia | Qué gana | Qué resigna | Hallazgo del TP2 que la sustenta o la descarta |
|---|---|---|---|---|
| **A — Paso a paso guiado** | Facilidad de aprendizaje | Cada pantalla tiene un solo objetivo y está explicada; nadie se pierde en el primer uso; acompaña a los dos usuarios de 1er año | Requiere 4 o 5 toques entre la notificación y el material; quien entra 3 o 5 veces por día repite todo el recorrido cada vez; muy poca información por pantalla | **La descarta:** los tres usuarios se declararon de perfil *"Avanzada: tengo facilidad para aprender y utilizar nuevas herramientas tecnológicas"*, con lo cual aprender la app no es la barrera. Además el uso es de alta frecuencia: *"Entre 1 y 5 veces, para verificar novedades o consultar material bibliográfico"* (Contador Público), *"2 o 3 veces en el día"* (Trabajo Social), *"unas 3 veces, para verificar mensajes"* (Kinesiología) |
| **B — Bandeja única densa** | Eficiencia | La novedad se resuelve desde la notificación o en un toque; una sola pantalla muestra qué pasó en todas las materias; el plan de estudios se lee con estados en vez de abrir un PDF | Pantalla cargada, con jerarquía menos obvia en el primer uso; mezcla en una misma lista lo académico y lo de Intraconsulta (separado sólo por filtro); pocas confirmaciones, más riesgo de pasar algo por alto al barrer rápido | **La sustenta:** el motivo de ingreso es verificar y bajar, no explorar — *"Ver si hay nuevos mensajes o avisos., Descargar materiales., Consultar fechas o información de las materias."* (Contador Público) y *"unas 3 veces, para verificar mensajes y porq si"* (Kinesiología). Hay además fricción explícita de acceso: *"así ni tengo q abrir dos veces con mi contraseña"* (Kinesiología). Sobre correlatividades: *"Consulto el plan de estudios descargado previamente"* (Contador Público) |
| **C — Estado del día con secciones** | Satisfacción (control y tranquilidad) | Responde de frente al miedo a enterarse tarde: dice explícitamente si está al día; separa lo académico de lo institucional; acciones reversibles y notificaciones configurables | Agrega un nivel de navegación antes de llegar al contenido y confirma acciones de bajo riesgo (descargar un PDF), lo que penaliza al usuario recurrente; las preferencias exigen una configuración inicial | **La sustenta parcialmente:** los tres respondieron *"Sí, alguna vez"* haberse enterado tarde de un aviso, y el usuario de Trabajo Social pide separar: *"Creo que no. Ya que es mejor separar la parte administrativa, de la academica."* **La limita:** es 1 de 3 usuarios; los otros dos piden lo contrario — *"podria ser util tanto tener las plataformas unificadas en una sola"* (Contador Público) y *"estaría bueno entrar de una a miel y q aparezca una opción de pasarse a intraconsulta"* (Kinesiología) |

### Alternativa elegida: B — Bandeja única densa (eficiencia)

Elegimos la alternativa B porque el uso que describieron los tres usuarios es de alta frecuencia
y corta duración: entran a MIEL entre una y cinco veces por día, casi siempre para lo mismo —ver
si hay algo nuevo, bajar un material y mirar una fecha— y ninguno describe una tarea de
exploración. En un uso así, el costo de cada paso se multiplica por decenas de entradas durante
el cuatrimestre, y por eso el atributo que más valor aporta es la eficiencia: que la notificación
lleve directo al contenido y que una sola pantalla responda "qué pasó en mis materias". La
facilidad de aprendizaje, que sería la razón para elegir A, no aparece como problema en el
relevamiento: los tres usuarios se declararon de perfil tecnológico avanzado, así que optimizar
para el primer uso sería optimizar un problema que este usuario no tiene. La elección también es
coherente con los dos atributos priorizados en el brief v3, porque en este contexto la
satisfacción está ligada a dejar de perder tiempo entrando a dos plataformas sólo para chequear
si hay novedades.

**Qué costo tiene esta elección.** Resignamos tres cosas concretas. Primero, el acompañamiento de
la alternativa A: la bandeja densa es más difícil de interpretar en el primer uso, y los usuarios
de 1er año (Trabajo Social y Kinesiología) van a tener que descubrir la estructura sin guía.
Segundo, la separación fuerte entre lo académico y lo administrativo que pidió explícitamente el
usuario de Trabajo Social: en B esa separación existe como filtro y como la sección "Plan", no
como dos espacios distintos; es una necesidad relevada que atendemos de forma parcial y a
conciencia. Tercero, las confirmaciones y la reversibilidad de la alternativa C: al barrer una
lista densa es más fácil pasar por alto una novedad, que es justamente el problema que el MVP
quiere resolver. Para acotar ese último riesgo tomamos un único elemento de C —el estado
leído/no leído y el agrupamiento por materia dentro de la bandeja—, aceptando que agrega
densidad a la pantalla. Si al probar el MVP los usuarios de 1er año se pierden en el Home, la
alternativa A queda documentada como plan de contingencia.
