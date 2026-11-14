package com.example.proyectobd2.models;

import java.time.LocalDate;

public class Deposito {
    private Long id;
    private String nombreDeposito;
    private Double capacidadLitros;
    private Double nivelActualLitros;
    private String tipoDeposito;
    private String ubicacion;
    private LocalDate fechaInstalacion;
    private String estado;
    private Long coloniaId;

    public Deposito(Long id, String nombreDeposito, Double capacidadLitros, Double nivelActualLitros,
                    String tipoDeposito, String ubicacion, LocalDate fechaInstalacion,
                    String estado, Long coloniaId) {
        this.id = id;
        this.nombreDeposito = nombreDeposito;
        this.capacidadLitros = capacidadLitros;
        this.nivelActualLitros = nivelActualLitros;
        this.tipoDeposito = tipoDeposito;
        this.ubicacion = ubicacion;
        this.fechaInstalacion = fechaInstalacion;
        this.estado = estado;
        this.coloniaId = coloniaId;
    }

    public Long getId() { return id; }
    public String getNombreDeposito() { return nombreDeposito; }
    public Double getCapacidadLitros() { return capacidadLitros; }
    public Double getNivelActualLitros() { return nivelActualLitros; }
    public String getTipoDeposito() { return tipoDeposito; }
    public String getUbicacion() { return ubicacion; }
    public LocalDate getFechaInstalacion() { return fechaInstalacion; }
    public String getEstado() { return estado; }
    public Long getColoniaId() { return coloniaId; }
}

