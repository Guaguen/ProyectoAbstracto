package com.example.proyectobd2.dto;

public record LoginResponse(
        String token,
        UsuarioResponse user) {
}