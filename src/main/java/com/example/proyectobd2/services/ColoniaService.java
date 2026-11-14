package com.example.proyectobd2.services;

import com.example.proyectobd2.dto.CreateColoniaRequest;
import com.example.proyectobd2.models.Colonia;
import com.example.proyectobd2.structures.LinkedList;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColoniaService {

    private final LinkedList<Colonia> colonias = new LinkedList<>();

    public ColoniaService() {
        colonias.add(new Colonia(
                1L, "Centro", "Puebla", "72000",
                "Zona centro de Puebla", 19.0433, -98.1982, "Activo"
        ));

        colonias.add(new Colonia(
                2L, "La Paz", "Puebla", "72160",
                "Zona residencial", 19.0520, -98.2250, "Activo"
        ));

        colonias.add(new Colonia(
                3L, "San Manuel", "Puebla", "72570",
                "Zona universitaria", 19.0210, -98.1980, "Activo"
        ));
    }

    public List<Colonia> obtenerTodas() {
        return colonias.toList();
    }

    public Colonia obtenerPorId(Long id) {
        return colonias.find(c -> c.getId().equals(id));
    }

    public Colonia crear(CreateColoniaRequest request) {
        Long nuevoId = (long) (colonias.size() + 1);

        Colonia nuevaColonia = new Colonia(
                nuevoId,
                request.nombreColonia(),
                request.municipio(),
                request.codigoPostal(),
                request.descripcion(),
                request.latitud(),
                request.longitud(),
                request.estado()
        );

        colonias.add(nuevaColonia);

        return nuevaColonia;
    }
}