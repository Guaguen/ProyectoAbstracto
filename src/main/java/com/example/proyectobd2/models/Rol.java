package com.example.proyectobd2.models;

public class Rol {
    private Long id;
    private String nombreRol;
    private String descripcion;
    private String estado;

    public Rol(Long id, String nombreRol, String descripcion, String estado) {
        this.id = id;
        this.nombreRol = nombreRol;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public String getNombreRol() { return nombreRol; }
    public String getDescripcion() { return descripcion; }
    public String getEstado() { return estado; }
}