# EVALUACIÓN HEURÍSTICA DE USABILIDAD

## Información general del informe

| Campo | Detalle |
| --- | --- |
| **Fecha** | 23/09/2026 |
| **Producto estudiado** | Unlam Parking (Aparcamiento Unlam) — Flujo principal del MVP, Alternativa B (propuesta final) |
| **Grupo que evalúa** | Equipo ágil |

## Checklist

| **ID** | **Heurística** | **Total** | **Parcial** | **Nulo** | **N/A** | **Cant. hallazgos** | **Puntuación (0-5)** | **Observaciones** |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| **H1** | Visibilidad del estado del sistema | | ✔ | | | 2 | 3 | Buen manejo del estado de reserva en la pantalla de inicio (Alternativa B), pero falta feedback de disponibilidad y de validación en el ingreso. |
| **H2** | Correspondencia entre el sistema y el mundo real | ✔ | | | | 0 | 4 | Los conceptos (reservar, cancelar, QR, barrera) coinciden con el modelo mental real del usuario. |
| **H3** | Libertad y control del usuario | | ✔ | | | 1 | 3 | Existe cancelación de reserva, pero falta libertad de retroceso/cancelación durante la carga de fecha y hora. |
| **H4** | Prevención de errores | | ✔ | | | 2 | 2 | El límite de 24 hs ayuda a acotar errores de rango, pero falta confirmación previa a acciones destructivas y validación proactiva de horarios. |
| **H5** | Consistencia y estándares | | ✔ | | | 1 | 3 | Riesgo de inconsistencia si se combinan patrones visuales de distintas alternativas sin un sistema de diseño único. |
| **H6** | Reconocimiento en vez de recordar | ✔ | | | | 0 | 5 | Es el atributo mejor resuelto: la Alternativa B seleccionada prioriza mostrar el estado de la reserva directamente en el inicio. |
| **H7** | Flexibilidad y eficiencia de uso | | ✔ | | | 1 | 2 | La sesión persistente ayuda, pero no hay atajos ni accesos rápidos para usuarios frecuentes. |
| **H8** | Diseño estético y minimalista | | ✔ | | | 1 | 3 | Diseño minimalista logrado en B, aunque el botón contextual único (que cambia de acción) puede resultar ambiguo. |
| **H9** | Reconocimiento, diagnóstico y recuperación ante errores | | | ✔ | | 2 | 1 | No hay mensajes de error definidos ni vías de recuperación, en especial ante fallas de disponibilidad o de lectura del QR. |
| **H10** | Ayuda y documentación | | | ✔ | | 1 | 1 | No se contempla ninguna sección de ayuda, tutorial o documentación dentro de la aplicación. |

## Matriz de hallazgos

| **#** | **Heurística involucrada** | **Detalle** | **Severidad (1-4)** | **Mejora sugerida** |
| --- | --- | --- | --- | --- |
| 1 | H1 | No se muestra la disponibilidad de lugares en tiempo real antes de confirmar la reserva; recién se informa "si hay disponibilidad" al momento de confirmar. | 3 | Mostrar la cantidad de lugares disponibles directamente en la pantalla de reserva, antes de que el usuario confirme. |
| 2 | H1 | No hay indicador de carga o progreso durante el escaneo del QR o el levantamiento de la barrera. | 2 | Agregar feedback visual (spinner, mensaje "Validando...") durante el proceso de ingreso. |
| 3 | H3 | No se menciona una opción de retroceso o cancelación mientras el usuario completa fecha y hora de la reserva. | 2 | Incluir un botón de retroceso/cancelar en cada paso del flujo de reserva. |
| 4 | H4 | No existe confirmación previa al cancelar una reserva ya realizada, con riesgo de cancelación accidental. | 3 | Agregar un diálogo de confirmación ("¿Seguro que desea cancelar su reserva?"). |
| 5 | H4 | No se valida de forma visible que la fecha/hora elegida sea futura y esté dentro del rango de 24 hs mientras se completa el formulario. | 2 | Deshabilitar directamente en el selector los horarios inválidos, en lugar de mostrar el error después de enviar. |
| 6 | H5 | Las alternativas A, B y C presentan patrones visuales distintos; si se combinan elementos de más de una sin un sistema de diseño unificado, se genera inconsistencia. | 2 | Definir una guía de estilo única antes de construir, para asegurar consistencia entre todas las pantallas. |
| 7 | H7 | No existen atajos para usuarios frecuentes, como reservar nuevamente el horario habitual. | 1 | Agregar un acceso rápido para repetir la última reserva o el turno más usado. |
| 8 | H8 | En la Alternativa B, el botón único de la pantalla de inicio cambia de acción según el estado ("reservar" vs "ver QR"), lo que puede ser ambiguo si no está bien etiquetado. | 2 | Etiquetar claramente el botón según el estado actual ("Reservar" o "Ver reserva/QR"). |
| 9 | H9 | No está definido qué mensaje recibe el usuario cuando no hay disponibilidad al momento de reservar. | 3 | Diseñar un mensaje de error específico que sugiera franjas horarias alternativas con disponibilidad. |
| 10 | H9 | No se contempla qué sucede si falla el escaneo del QR en la barrera (lectura fallida, reserva vencida, etc.). | 4 | Definir mensajes de error claros y una vía de contingencia (por ejemplo, contacto con seguridad) para fallos de validación en el ingreso. |
| 11 | H10 | No existe ninguna sección de ayuda, tutorial o documentación dentro de la app, pese a que "facilidad de aprendizaje" es un atributo de usabilidad priorizado. | 2 | Incorporar un tutorial breve en el primer uso, o un ícono de ayuda accesible desde el menú principal. |

## Conclusiones / Recomendaciones generales

La Alternativa B (seleccionada como propuesta final) resuelve bien el atributo priorizado de **recuerdo en el tiempo** (H6) al mostrar el estado de la reserva directamente en la pantalla de inicio, evitando que el usuario tenga que recordar dónde buscarlo. Sin embargo, el flujo presenta debilidades importantes en **prevención y recuperación de errores** (H4, H9), particularmente en el momento crítico de validación en la barrera de ingreso, donde una falla sin manejo definido podría anular el beneficio central de la propuesta de valor (evitar demoras). También falta soporte de **ayuda/documentación** (H10), relevante dado que el producto busca ser fácil de aprender incluso para usuarios que no lo usan a diario. Se recomienda priorizar, antes de construir el MVP: (1) definir los mensajes de error del flujo de reserva y de validación por QR, (2) agregar confirmación ante la cancelación de una reserva, y (3) fijar un sistema de diseño único para evitar inconsistencias visuales entre pantallas.
