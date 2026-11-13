package com.example.proyectobd2.controllers;

import com.example.proyectobd2.dto.CreateRolRequest;
import com.example.proyectobd2.models.Rol;
import com.example.proyectobd2.services.RolService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping
    public List<Rol> obtenerTodos() {
        return rolService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rol> obtenerPorId(@PathVariable Long id) {
        Rol rol = rolService.obtenerPorId(id);
        if (rol == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(rol);
    }

    @PostMapping
    public ResponseEntity<Rol> crear(@RequestBody CreateRolRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rolService.crear(request));
    }
}