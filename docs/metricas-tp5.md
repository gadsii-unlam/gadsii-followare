# Parte 1 — Antes de construir

Documento correspondiente al punto 1 del TP5. Queda commiteado **antes** de escribir la
primera línea del MVP: define qué se va a medir en la sesión con usuarios y qué resultado
confirmaría o refutaría la hipótesis del TP2.

---

## 1. Métricas y criterio de éxito

### Métricas accionables

#### Resolución directa desde la notificación

Cantidad de usuarios (de 3) que, ante una notificación de novedad, realizan la acción que
ofrece o acceden a la información que comunica (por ejemplo, descargar el material, o indicar
el aula nueva), usando solo la notificación o el panel al que enlaza, sin navegar a otras
secciones ni pedir ayuda.

**Qué decisión habilita:** si 2 o más no lo logran, se rediseña la acción de la notificación.
Si 2 o 3 lo logran, se avanza.

#### Tareas completadas sin ayuda

Cantidad de tareas (de un set fijo de 3, que combina contenido de materias, de trayectoria
académica y cambio de aula) que cada usuario completa usando solo Followare, sin pedir ayuda
ni recibir pistas del moderador. Se registra además el camino que siguió cada usuario.

**Qué decisión habilita:** si una tarea falla en 2 o más usuarios, ese contenido no está bien
resuelto en la app y se revisa dónde vive o cómo se llega a él. Si todas se completan por al
menos 2 usuarios, la centralización sostiene la hipótesis.

---

### Métrica vanidosa

#### Aperturas de la app por día

Un número alto de ingresos suele leerse como éxito de retención y uso activo, pero no permite
concluir nada sobre la hipótesis. Followare busca que el estudiante **no** tenga que entrar
solo a comprobar si hay novedades.

En el TP2 detectamos que los encuestados ingresan a MIeL hasta 5 veces por día, en buena parte
para consultar mensajes o avisos, y los tres se enteraron tarde de algo alguna vez. Si en
Followare se observara una alta cantidad de ingresos espontáneos, no indicaría éxito: podría
significar que el usuario no confía en que las notificaciones le avisaron a tiempo y sigue
entrando para verificar.

Además, la métrica no permite decidir qué corregir, porque no distingue entre un ingreso que
resolvió una tarea y uno que fue a chequear.

---

### Criterio de éxito y refutación de la hipótesis

**Se sostiene** si al menos 2 de los 3 usuarios resuelven la notificación sin ayuda y cada una
de las 3 tareas es completada sin ayuda por al menos 2 usuarios.

**Se refuta** si 2 o más usuarios no resuelven la notificación sin ayuda, o si una misma tarea
falla en 2 o más usuarios.

**Se sostiene parcialmente** si se cumple solo una de las dos condiciones: se conserva la mitad
que funcionó y se rediseña la otra.

Aclaraciones:

* Una falla atribuible a una limitación del prototipo (por ejemplo, una pantalla no navegable)
  no se cuenta contra la hipótesis.
* Con 3 usuarios, los resultados se leen como señal cualitativa, no como evidencia estadística.
