package com.example.proyectobd2.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;

public class Usuario {
    private Long id;
    private String nombreUsuario;
    private String correo;
    @JsonIgnore
    private String contrasenaHash;
    private String telefono;
    private LocalDate fechaRegistro;
    private String estado;
    private Long rolId;

    public Usuario(Long id, String nombreUsuario, String correo, String contrasenaHash,
                   String telefono, LocalDate fechaRegistro, String estado, Long rolId) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.contrasenaHash = contrasenaHash;
        this.telefono = telefono;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.rolId = rolId;
    }

    public Long getId() { return id; }
    public String getNombreUsuario() { return nombreUsuario; }
    public String getCorreo() { return correo; }
    public String getContrasenaHash() { return contrasenaHash; }
    public String getTelefono() { return telefono; }
    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public String getEstado() { return estado; }
    public Long getRolId() { return rolId; }
}