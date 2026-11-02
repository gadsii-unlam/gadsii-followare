package com.unlam.followare.data;

import com.unlam.followare.models.Archivo;
import com.unlam.followare.models.EstadoMateria;
import com.unlam.followare.models.Materia;
import com.unlam.followare.models.MateriaPlan;
import com.unlam.followare.models.Novedad;
import com.unlam.followare.models.TipoNovedad;
import com.unlam.followare.models.Unidad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SeedData {

    public static List<Materia> getInitialMaterias() {
        List<Materia> list = new ArrayList<>();

        // 1. Álgebra I
        List<Archivo> archU1 = Arrays.asList(
                new Archivo("arch-alg-1", "teoria-matrices.pdf", "2,4 MB", "pdf", true),
                new Archivo("arch-alg-2", "ejercicios-unidad-1.pdf", "1,8 MB", "pdf", true)
        );
        List<Archivo> archU2 = Collections.singletonList(
                new Archivo("arch-alg-3", "guia-unidad-2.pdf", "1,2 MB", "pdf", false)
        );
        List<Unidad> unidadesAlg = Arrays.asList(
                new Unidad(1, "Sistemas de Ecuaciones Lineales y Matrices", archU1),
                new Unidad(2, "Espacios Vectoriales y Transformaciones", archU2)
        );
        list.add(new Materia("mat-algebra-1", "MAT101", "Álgebra I", "Comisión 3",
                "Prof. Martínez", "Lunes y Miércoles 19:00 - 22:00", unidadesAlg));

        // 2. Química General
        List<Archivo> archQui = Collections.singletonList(
                new Archivo("arch-qui-1", "tabla-periodica-guia.pdf", "0,9 MB", "pdf", false)
        );
        List<Unidad> unidadesQui = Collections.singletonList(
                new Unidad(1, "Estructura Atómica y Enlace", archQui)
        );
        list.add(new Materia("mat-quimica", "QUI102", "Química General", "Comisión 1",
                "Prof. Gómez", "Martes 18:00 - 22:00", unidadesQui));

        // 3. Análisis Matemático I
        List<Archivo> archAn1 = Collections.singletonList(
                new Archivo("arch-an1-1", "teoremas-limites.pdf", "1,5 MB", "pdf", false)
        );
        List<Unidad> unidadesAn1 = Collections.singletonList(
                new Unidad(1, "Límites y Continuidad", archAn1)
        );
        list.add(new Materia("mat-analisis-1", "MAT103", "Análisis Matemático I", "Comisión 2",
                "Prof. Rossi", "Viernes 18:00 - 22:00", unidadesAn1));

        return list;
    }

    public static List<Novedad> getInitialNovedades() {
        List<Novedad> list = new ArrayList<>();
        long now = System.currentTimeMillis();

        Archivo archGuia2 = new Archivo("arch-alg-3", "guia-unidad-2.pdf", "1,2 MB", "pdf", false);

        list.add(new Novedad("nov-alg-material-2", "mat-algebra-1", "ÁLGEBRA I", "Com. 3",
                TipoNovedad.MATERIAL, "Nuevo material disponible: Guía Unidad 2",
                "Estimados alumnos, ya se encuentra disponible para su descarga la Guía de Ejercicios correspondiente a la Unidad 2 (Espacios Vectoriales). Por favor resolver los ejercicios 1 al 15 para la clase práctica del próximo lunes.",
                "hoy 14:20", now - 3600000, false, true, archGuia2));

        list.add(new Novedad("nov-alg-mensaje-1", "mat-algebra-1", "ÁLGEBRA I", "Com. 3",
                TipoNovedad.MENSAJE, "Mensaje del profesor: Cambio de aula",
                "Buenas tardes, les informamos que este jueves la clase teórica se dictará excepcionalmente en el Aula 312 del Edificio Principal debido a tareas de mantenimiento en el aula habitual.",
                "hoy 09:10", now - 18000000, false, true, null));

        list.add(new Novedad("nov-qui-fecha-1", "mat-quimica", "QUÍMICA", "Com. 1",
                TipoNovedad.FECHA, "Fecha de 1° Parcial cargada",
                "Se confirmó la fecha para la primera evaluación parcial presencial: martes 22 de septiembre a las 18:30 hs. Contenidos: Unidades 1 y 2.",
                "ayer", now - 86400000, true, false, null));

        list.add(new Novedad("nov-intra-finales", "materia-unlam", "INTRACONSULTA", "",
                TipoNovedad.INSTITUCIONAL, "Inscripción a finales: Turno Noviembre",
                "El Departamento de Alumnos informa que el período de inscripción para el turno ordinario de exámenes finales abrirá el próximo 15 de septiembre a través del sistema Intraconsulta.",
                "ayer", now - 90000000, false, false, null));

        return list;
    }

    public static List<MateriaPlan> getInitialPlanEstudios() {
        List<MateriaPlan> list = new ArrayList<>();

        list.add(new MateriaPlan("INF101", "Fundamentos de Informática", 1, 1,
                EstadoMateria.APROBADA, 9, Collections.emptyList(), Collections.emptyList()));

        list.add(new MateriaPlan("MAT101", "Álgebra I", 1, 1,
                EstadoMateria.CURSANDO, null, Collections.emptyList(), Collections.emptyList()));

        list.add(new MateriaPlan("QUI102", "Química General", 1, 1,
                EstadoMateria.CURSANDO, null, Collections.emptyList(), Collections.emptyList()));

        list.add(new MateriaPlan("MAT103", "Análisis Matemático I", 1, 1,
                EstadoMateria.APROBADA, 8, Collections.emptyList(), Collections.emptyList()));

        list.add(new MateriaPlan("FIS104", "Física I", 1, 2,
                EstadoMateria.HABILITADA, null, Collections.singletonList("MAT103"), Collections.emptyList()));

        list.add(new MateriaPlan("MAT201", "Análisis Matemático II", 2, 1,
                EstadoMateria.BLOQUEADA, null, Arrays.asList("MAT101", "MAT103"),
                Collections.singletonList("Álgebra I (Pendiente de aprobación)")));

        list.add(new MateriaPlan("INF201", "Arquitectura de Computadoras", 2, 1,
                EstadoMateria.HABILITADA, null, Collections.singletonList("INF101"), Collections.emptyList()));

        list.add(new MateriaPlan("INF202", "Algoritmos y Programación", 2, 1,
                EstadoMateria.HABILITADA, null, Arrays.asList("MAT101", "INF101"), Collections.emptyList()));

        list.add(new MateriaPlan("FIS201", "Física II", 2, 2,
                EstadoMateria.BLOQUEADA, null, Arrays.asList("FIS104", "MAT103"),
                Collections.singletonList("Física I (Pendiente de aprobación)")));

        list.add(new MateriaPlan("INF203", "Estructuras de Datos", 2, 2,
                EstadoMateria.BLOQUEADA, null, Collections.singletonList("INF202"),
                Collections.singletonList("Algoritmos y Programación (Pendiente)")));

        list.add(new MateriaPlan("MAT301", "Probabilidad y Estadística", 3, 1,
                EstadoMateria.BLOQUEADA, null, Collections.singletonList("MAT201"),
                Collections.singletonList("Análisis Matemático II (Pendiente)")));

        list.add(new MateriaPlan("INF301", "Bases de Datos", 3, 1,
                EstadoMateria.BLOQUEADA, null, Collections.singletonList("INF202"),
                Collections.singletonList("Algoritmos y Programación (Pendiente)")));

        list.add(new MateriaPlan("INF303", "Sistemas Operativos", 3, 2,
                EstadoMateria.BLOQUEADA, null, Arrays.asList("INF201", "INF203"),
                Arrays.asList("Arquitectura de Computadoras (Pendiente)", "Estructuras de Datos (Pendiente)")));

        return list;
    }
}

