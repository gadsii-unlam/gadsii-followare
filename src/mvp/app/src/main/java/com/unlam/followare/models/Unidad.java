package com.unlam.followare.models;

import java.util.List;

public class Unidad {
    private int numero;
    private String titulo;
    private List<Archivo> archivos;

    public Unidad(int numero, String titulo, List<Archivo> archivos) {
        this.numero = numero;
        this.titulo = titulo;
        this.archivos = archivos;
    }

    public int getNumero() { return numero; }
    public String getTitulo() { return titulo; }
    public List<Archivo> getArchivos() { return archivos; }
}

