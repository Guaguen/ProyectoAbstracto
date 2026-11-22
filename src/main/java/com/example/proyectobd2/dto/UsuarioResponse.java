package com.example.proyectobd2.dto;

import com.example.proyectobd2.models.Usuario;

import java.util.List;

public record UsuarioResponse(int id, String name, String email, String role, List<String> roles) {

    public static UsuarioResponse de(Usuario usuario) {
        return new UsuarioResponse(
                usuario.id(),
                usuario.nombre(),
                usuario.correo(),
                usuario.rol(),
                List.of(usuario.rol()));
    }
}