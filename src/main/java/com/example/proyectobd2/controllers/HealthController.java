
package com.example.proyectobd2.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    private final JdbcTemplate jdbcTemplate;

    public HealthController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public String health() {
        return "OK";
    }

    @GetMapping("/db")
    public ResponseEntity<String> healthDatabase() {
        try {
            Integer result = jdbcTemplate.queryForObject(
                    "SELECT 1", Integer.class
            );

            if (result != null && result == 1) {
                return ResponseEntity.ok(
                        "Conexion a PostgreSQL exitosa"
                );
            }

            return ResponseEntity.internalServerError()
                    .body("Error en la consulta");

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("No se pudo conectar a PostgreSQL");
        }
    }
}
