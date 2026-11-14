package com.example.proyectobd2.dto;

public record CreateColoniaRequest(
        String nombreColonia,
        String municipio,
        String codigoPostal,
        String descripcion,
        Double latitud,
        Double longitud,
        String estado
) {}