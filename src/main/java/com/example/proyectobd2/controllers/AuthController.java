package com.example.proyectobd2.controllers;

import com.example.proyectobd2.dto.LoginRequest;
import com.example.proyectobd2.dto.LoginResponse;
import com.example.proyectobd2.dto.RegistroRequest;
import com.example.proyectobd2.dto.UsuarioResponse;
import com.example.proyectobd2.dto.UsuarioToken;
import com.example.proyectobd2.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registro")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse registrar(@Valid @RequestBody RegistroRequest request) {
        return authService.registrar(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/yo")
    public UsuarioResponse yo(@RequestAttribute("usuario") UsuarioToken usuario) {
        return authService.yo(usuario.id());
    }
}