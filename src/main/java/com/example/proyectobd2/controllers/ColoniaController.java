package com.example.proyectobd2.controllers;

import com.example.proyectobd2.dto.CreateColoniaRequest;
import com.example.proyectobd2.models.Colonia;
import com.example.proyectobd2.services.ColoniaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/colonias")
public class ColoniaController {

    private final ColoniaService coloniaService;

    public ColoniaController(ColoniaService coloniaService) {
        this.coloniaService = coloniaService;
    }

    @GetMapping
    public ResponseEntity<?> obtenerTodas() {
        return ResponseEntity.ok(coloniaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {

        Colonia colonia = coloniaService.obtenerPorId(id);

        if (colonia == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(colonia);
    }

    @PostMapping
    public ResponseEntity<Colonia> crear(
            @RequestBody CreateColoniaRequest request) {

        Colonia nuevaColonia = coloniaService.crear(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaColonia);
    }
}