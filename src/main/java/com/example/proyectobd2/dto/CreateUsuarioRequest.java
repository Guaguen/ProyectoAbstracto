package com.example.proyectobd2.dto;

public record CreateUsuarioRequest(
        String nombreUsuario,
        String correo,
        String contrasena,
        String telefono,
        Long rolId
) {}