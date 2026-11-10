package com.unlam.followare.models;

public class Novedad {
    private String id;
    private String materiaId;
    private String materiaNombre;
    private String comision;
    private TipoNovedad tipo;
    private String titulo;
    private String cuerpo;
    private String fecha;
    private long timestamp;
    private boolean leida;
    private boolean urgente;
    private Archivo archivoAdjunto;

    public Novedad(String id, String materiaId, String materiaNombre, String comision,
                   TipoNovedad tipo, String titulo, String cuerpo, String fecha,
                   long timestamp, boolean leida, boolean urgente, Archivo archivoAdjunto) {
        this.id = id;
        this.materiaId = materiaId;
        this.materiaNombre = materiaNombre;
        this.comision = comision;
        this.tipo = tipo;
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.fecha = fecha;
        this.timestamp = timestamp;
        this.leida = leida;
        this.urgente = urgente;
        this.archivoAdjunto = archivoAdjunto;
    }

    public String getId() { return id; }
    public String getMateriaId() { return materiaId; }
    public String getMateriaNombre() { return materiaNombre; }
    public String getComision() { return comision; }
    public TipoNovedad getTipo() { return tipo; }
    public String getTitulo() { return titulo; }
    public String getCuerpo() { return cuerpo; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    public boolean isLeida() { return leida; }
    public void setLeida(boolean leida) { this.leida = leida; }
    public boolean isUrgente() { return urgente; }
    public Archivo getArchivoAdjunto() { return archivoAdjunto; }
}

