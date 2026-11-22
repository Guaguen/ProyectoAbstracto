package com.example.proyectobd2.models;

// Usuario del login (se guarda en Neon, tabla Usuarios).
public record Usuario(
        int id,
        String nombre,
        String correo,
        String contrasenaHash,
        String estado,
        String rol) {
}