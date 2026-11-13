package com.example.proyectobd2.services;

import com.example.proyectobd2.dto.CreateRolRequest;
import com.example.proyectobd2.models.Rol;
import com.example.proyectobd2.structures.LinkedList;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {

    private final LinkedList<Rol> roles = new LinkedList<>();
    private Long siguienteId = 1L;

    public RolService() {
        roles.add(new Rol(siguienteId++, "Administrador", "Gestiona todo el sistema", "activo"));
        roles.add(new Rol(siguienteId++, "Tecnico", "Atiende y repara reportes", "activo"));
        roles.add(new Rol(siguienteId++, "Ciudadano", "Registra reportes y consulta abastecimiento", "activo"));
    }

    public List<Rol> obtenerTodos() {
        return roles.toList();
    }

    public Rol obtenerPorId(Long id) {
        return roles.find(r -> r.getId().equals(id));
    }

    public Rol crear(CreateRolRequest request) {
        Rol rol = new Rol(siguienteId++, request.nombreRol(), request.descripcion(), request.estado());
        roles.add(rol);
        return rol;
    }
}