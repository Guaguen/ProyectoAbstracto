package com.example.proyectobd2.services;

import com.example.proyectobd2.dto.CreateDepositoRequest;
import com.example.proyectobd2.models.Deposito;
import com.example.proyectobd2.structures.LinkedList;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DepositoService {

    private final LinkedList<Deposito> depositos = new LinkedList<>();
    private Long siguienteId = 1L;

    public DepositoService() {
        depositos.add(new Deposito(siguienteId++, "Tanque Centro", 50000.0, 32000.0,
                "Tanque elevado", "Calle 5 de Mayo", LocalDate.of(2020, 3, 15), "operando", 1L));
        depositos.add(new Deposito(siguienteId++, "Cisterna La Paz", 30000.0, 5000.0,
                "Cisterna", "Av. Juarez", LocalDate.of(2019, 8, 1), "nivel bajo", 2L));
        depositos.add(new Deposito(siguienteId++, "Tanque San Manuel", 40000.0, 28000.0,
                "Tanque superficial", "Av. San Claudio", LocalDate.of(2021, 1, 20), "operando", 3L));
    }

    public List<Deposito> obtenerTodos() {
        return depositos.toList();
    }

    public Deposito obtenerPorId(Long id) {
        return depositos.find(d -> d.getId().equals(id));
    }

    public Deposito crear(CreateDepositoRequest request) {
        Deposito deposito = new Deposito(siguienteId++, request.nombreDeposito(),
                request.capacidadLitros(), request.nivelActualLitros(), request.tipoDeposito(),
                request.ubicacion(), request.fechaInstalacion(), request.estado(), request.coloniaId());
        depositos.add(deposito);
        return deposito;
    }
}

