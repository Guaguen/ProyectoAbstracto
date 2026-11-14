package com.example.proyectobd2.services;

import com.example.proyectobd2.dto.CreateReporteRequest;
import com.example.proyectobd2.models.Reporte;
import com.example.proyectobd2.structures.LinkedList;
import com.example.proyectobd2.structures.Queue;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReporteService {

    private final LinkedList<Reporte> reportes = new LinkedList<>();
    private final Queue<Reporte> pendientes = new Queue<>();
    private Long siguienteId = 1L;

    public ReporteService() {
        registrar(new Reporte(siguienteId++, "Fuga en calle principal",
                "Sale agua de la banqueta desde hace dos dias",
                LocalDate.of(2026, 11, 10), LocalTime.of(9, 30), "Calle 5 de Mayo #120",
                "fuga1.jpg", "alta", "pendiente", 3L, 1L));
        registrar(new Reporte(siguienteId++, "Sin agua desde ayer",
                "No llega agua a la vivienda desde el lunes",
                LocalDate.of(2026, 11, 11), LocalTime.of(14, 15), "Av. Juarez #45",
                "sinagua.jpg", "media", "pendiente", 3L, 2L));
        registrar(new Reporte(siguienteId++, "Agua turbia",
                "El agua sale de color cafe",
                LocalDate.of(2026, 11, 12), LocalTime.of(8, 0), "Av. San Claudio #300",
                "turbia.jpg", "alta", "pendiente", 3L, 3L));
    }

    private void registrar(Reporte reporte) {
        reportes.add(reporte);
        pendientes.enqueue(reporte);
    }

    public List<Reporte> obtenerTodos() {
        return reportes.toList();
    }

    public Reporte obtenerPorId(Long id) {
        return reportes.find(r -> r.getId().equals(id));
    }

    public Reporte crear(CreateReporteRequest request) {
        Reporte reporte = new Reporte(siguienteId++, request.titulo(), request.descripcion(),
                LocalDate.now(), LocalTime.now().withNano(0), request.ubicacion(),
                request.fotografia(), request.nivelPrioridad(), "pendiente",
                request.usuarioId(), request.coloniaId());
        registrar(reporte);
        return reporte;
    }

    public List<Reporte> obtenerPendientes() {
        return pendientes.toList();
    }

    public Reporte atenderSiguiente() {
        Reporte reporte = pendientes.dequeue();
        if (reporte != null) {
            reporte.setEstado("en atencion");
        }
        return reporte;
    }
}