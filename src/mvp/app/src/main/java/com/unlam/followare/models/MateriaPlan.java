package com.unlam.followare.models;

import java.util.List;

public class MateriaPlan {
    private String codigo;
    private String nombre;
    private int anio;
    private int cuatrimestre;
    private EstadoMateria estado;
    private Integer notaFinal;
    private List<String> correlativasRequeridas;
    private List<String> correlativasFaltantes;

    public MateriaPlan(String codigo, String nombre, int anio, int cuatrimestre,
                       EstadoMateria estado, Integer notaFinal,
                       List<String> correlativasRequeridas, List<String> correlativasFaltantes) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.anio = anio;
        this.cuatrimestre = cuatrimestre;
        this.estado = estado;
        this.notaFinal = notaFinal;
        this.correlativasRequeridas = correlativasRequeridas;
        this.correlativasFaltantes = correlativasFaltantes;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public int getAnio() { return anio; }
    public int getCuatrimestre() { return cuatrimestre; }
    public EstadoMateria getEstado() { return estado; }
    public Integer getNotaFinal() { return notaFinal; }
    public List<String> getCorrelativasRequeridas() { return correlativasRequeridas; }
    public List<String> getCorrelativasFaltantes() { return correlativasFaltantes; }
}

