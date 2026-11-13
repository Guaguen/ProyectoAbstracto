package com.example.proyectobd2.services;

import com.example.proyectobd2.dto.CreateUsuarioRequest;
import com.example.proyectobd2.models.Usuario;
import com.example.proyectobd2.structures.LinkedList;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UsuarioService {

    private final LinkedList<Usuario> usuarios = new LinkedList<>();
    private Long siguienteId = 1L;

    public UsuarioService() {
        usuarios.add(new Usuario(siguienteId++, "admin", "admin@aguaalerta.com",
                hash("admin123"), "2221234567", LocalDate.of(2026, 11, 1), "activo", 1L));
        usuarios.add(new Usuario(siguienteId++, "jperez", "jperez@aguaalerta.com",
                hash("tecnico123"), "2229876543", LocalDate.of(2026, 11, 3), "activo", 2L));
        usuarios.add(new Usuario(siguienteId++, "mlopez", "mlopez@correo.com",
                hash("ciudadano123"), "2225554433", LocalDate.of(2026, 11, 5), "activo", 3L));
    }

    // Hash simulado: en el ordinario se puede cambiar por BCrypt
    private String hash(String contrasena) {
        return Integer.toHexString(contrasena.hashCode());
    }

    public List<Usuario> obtenerTodos() {
        return usuarios.toList();
    }

    public Usuario obtenerPorId(Long id) {
        return usuarios.find(u -> u.getId().equals(id));
    }

    public Usuario crear(CreateUsuarioRequest request) {
        Usuario usuario = new Usuario(siguienteId++, request.nombreUsuario(), request.correo(),
                hash(request.contrasena()), request.telefono(), LocalDate.now(), "activo",
                request.rolId());
        usuarios.add(usuario);
        return usuario;
    }
}