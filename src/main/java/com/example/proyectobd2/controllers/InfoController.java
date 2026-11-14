package com.example.proyectobd2.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/info")
public class InfoController {

    @GetMapping
    public String obtenerInformacion() {
        return "AguaAlerta: Sistema de monitoreo y distribucion de agua potable";
    }
}