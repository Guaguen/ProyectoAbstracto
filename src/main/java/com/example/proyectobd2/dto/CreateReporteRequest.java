package com.example.proyectobd2.dto;

public record CreateReporteRequest(
String titulo,
String descripcion,
String ubicacion,
String fotografia,
String nivelPrioridad,
Long usuarioId,
Long coloniaId
) {}