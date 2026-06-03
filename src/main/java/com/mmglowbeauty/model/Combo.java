package com.mmglowbeauty.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "combos")
public class Combo {

    @Id
    private String id;
    private String nombre;
    private String descripcion;
    private Double precioCombo;
    private Double descuento;
    private List<String> servicioIds;
    private String anuncioId;

    public Combo() {
    }

    public Combo(String id, String nombre, String descripcion, Double precioCombo, Double descuento,
                 List<String> servicioIds, String anuncioId) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioCombo = precioCombo;
        this.descuento = descuento;
        this.servicioIds = servicioIds;
        this.anuncioId = anuncioId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecioCombo() {
        return precioCombo;
    }

    public void setPrecioCombo(Double precioCombo) {
        this.precioCombo = precioCombo;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public List<String> getServicioIds() {
        return servicioIds;
    }

    public void setServicioIds(List<String> servicioIds) {
        this.servicioIds = servicioIds;
    }

    public String getAnuncioId() {
        return anuncioId;
    }

    public void setAnuncioId(String anuncioId) {
        this.anuncioId = anuncioId;
    }

    @Override
    public String toString() {
        return "Combo{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precioCombo=" + precioCombo +
                ", descuento=" + descuento +
                ", servicioIds=" + servicioIds +
                ", anuncioId='" + anuncioId + '\'' +
                '}';
    }
}
