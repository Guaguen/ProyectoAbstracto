package com.example.proyectobd2.controllers;

import com.example.proyectobd2.dto.CreateDepositoRequest;
import com.example.proyectobd2.models.Deposito;
import com.example.proyectobd2.security.RequiereRol;
import com.example.proyectobd2.security.Roles;
import com.example.proyectobd2.services.DepositoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/depositos")
public class DepositoController {

    private final DepositoService depositoService;

    public DepositoController(DepositoService depositoService) {
        this.depositoService = depositoService;
    }

    @GetMapping
    @RequiereRol({Roles.ADMIN, Roles.TECNICO})
    public List<Deposito> obtenerTodos() {
        return depositoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    @RequiereRol({Roles.ADMIN, Roles.TECNICO})
    public ResponseEntity<Deposito> obtenerPorId(@PathVariable Long id) {
        Deposito deposito = depositoService.obtenerPorId(id);
        if (deposito == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(deposito);
    }

    @PostMapping
    @RequiereRol({Roles.ADMIN, Roles.TECNICO})
    public ResponseEntity<Deposito> crear(@RequestBody CreateDepositoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(depositoService.crear(request));
    }
}