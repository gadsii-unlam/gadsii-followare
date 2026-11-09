package com.unlam.followare.models;

public class Usuario {

    private final String nombre;
    private final String legajo;
    private final String email;
    private final String carrera;
    private final String sede;

    public Usuario(String nombre, String legajo, String email, String carrera, String sede) {
        this.nombre = nombre;
        this.legajo = legajo;
        this.email = email;
        this.carrera = carrera;
        this.sede = sede;
    }

    public String getNombre() {
        return nombre;
    }

    public String getLegajo() {
        return legajo;
    }

    public String getEmail() {
        return email;
    }

    public String getCarrera() {
        return carrera;
    }

    public String getSede() {
        return sede;
    }
}
