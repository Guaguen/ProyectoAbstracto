package com.example.proyectobd2.dto;

import java.time.LocalDate;

public record CreateDepositoRequest(
        String nombreDeposito,
        Double capacidadLitros,
        Double nivelActualLitros,
        String tipoDeposito,
        String ubicacion,
        LocalDate fechaInstalacion,
        String estado,
        Long coloniaId
) {}
