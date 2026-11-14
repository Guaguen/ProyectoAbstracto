package com.example.proyectobd2.controllers;

import com.example.proyectobd2.dto.CreateReporteRequest;
import com.example.proyectobd2.models.Reporte;
import com.example.proyectobd2.services.ReporteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping
    public List<Reporte> obtenerTodos() {
        return reporteService.obtenerTodos();
    }

    @GetMapping("/pendientes")
    public List<Reporte> obtenerPendientes() {
        return reporteService.obtenerPendientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reporte> obtenerPorId(@PathVariable Long id) {
        Reporte reporte = reporteService.obtenerPorId(id);
        if (reporte == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(reporte);
    }

    @PostMapping
    public ResponseEntity<Reporte> crear(@RequestBody CreateReporteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reporteService.crear(request));
    }

    @PostMapping("/atender")
    public ResponseEntity<Reporte> atenderSiguiente() {
        Reporte reporte = reporteService.atenderSiguiente();
        if (reporte == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(reporte);
    }
}