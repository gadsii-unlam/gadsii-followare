package com.unlam.followare.models;

import java.util.List;

public class Materia {
    private String id;
    private String codigo;
    private String nombre;
    private String comision;
    private String docente;
    private String horario;
    private List<Unidad> unidades;

    public Materia(String id, String codigo, String nombre, String comision, String docente, String horario, List<Unidad> unidades) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.comision = comision;
        this.docente = docente;
        this.horario = horario;
        this.unidades = unidades;
    }

    public String getId() { return id; }
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getComision() { return comision; }
    public String getDocente() { return docente; }
    public String getHorario() { return horario; }
    public List<Unidad> getUnidades() { return unidades; }
}

