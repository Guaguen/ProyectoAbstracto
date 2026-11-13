
package com.example.proyectobd2.models;

public class Colonia {

    private Long id;
    private String nombreColonia;
    private String municipio;
    private String codigoPostal;
    private String descripcion;
    private Double latitud;
    private Double longitud;
    private String estado;

    // Constructor
    public Colonia(Long id, String nombreColonia, String municipio,
                   String codigoPostal, String descripcion,
                   Double latitud, Double longitud, String estado) {

        this.id = id;
        this.nombreColonia = nombreColonia;
        this.municipio = municipio;
        this.codigoPostal = codigoPostal;
        this.descripcion = descripcion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.estado = estado;
    }

    // Getters
    public Long getId() { return id; }
    public String getNombreColonia() { return nombreColonia; }
    public String getMunicipio() { return municipio; }
    public String getCodigoPostal() { return codigoPostal; }
    public String getDescripcion() { return descripcion; }
    public Double getLatitud() { return latitud; }
    public Double getLongitud() { return longitud; }
    public String getEstado() { return estado; }
}
