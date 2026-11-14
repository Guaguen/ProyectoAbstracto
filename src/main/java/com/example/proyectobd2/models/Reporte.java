package com.example.proyectobd2.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reporte {
    private Long id;
    private String titulo;
    private String descripcion;
    private LocalDate fechaReporte;
    private LocalTime horaReporte;
    private String ubicacion;
    private String fotografia;
    private String nivelPrioridad;
    private String estado;
    private Long usuarioId;
    private Long coloniaId;

    public Reporte(Long id, String titulo, String descripcion, LocalDate fechaReporte,
                   LocalTime horaReporte, String ubicacion, String fotografia,
                   String nivelPrioridad, String estado, Long usuarioId, Long coloniaId) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaReporte = fechaReporte;
        this.horaReporte = horaReporte;
        this.ubicacion = ubicacion;
        this.fotografia = fotografia;
        this.nivelPrioridad = nivelPrioridad;
        this.estado = estado;
        this.usuarioId = usuarioId;
        this.coloniaId = coloniaId;
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public LocalDate getFechaReporte() { return fechaReporte; }
    public LocalTime getHoraReporte() { return horaReporte; }
    public String getUbicacion() { return ubicacion; }
    public String getFotografia() { return fotografia; }
    public String getNivelPrioridad() { return nivelPrioridad; }
    public String getEstado() { return estado; }
    public Long getUsuarioId() { return usuarioId; }
    public Long getColoniaId() { return coloniaId; }

    public void setEstado(String estado) { this.estado = estado; }
}