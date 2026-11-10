package com.unlam.followare.models;

public class Archivo {
    private String id;
    private String nombre;
    private String tamanoTexto;
    private String extension;
    private boolean descargado;

    public Archivo(String id, String nombre, String tamanoTexto, String extension, boolean descargado) {
        this.id = id;
        this.nombre = nombre;
        this.tamanoTexto = tamanoTexto;
        this.extension = extension;
        this.descargado = descargado;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getTamanoTexto() { return tamanoTexto; }
    public String getExtension() { return extension; }
    public boolean isDescargado() { return descargado; }
    public void setDescargado(boolean descargado) { this.descargado = descargado; }
}

