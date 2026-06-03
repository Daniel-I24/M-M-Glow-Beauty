package com.mmglowbeauty.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "servicios")
public class Servicio {

    @Id
    private String id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private int duracionMin;
    private String imagenUrl;
    private boolean activo;
    private String tipoServicioId;
    private List<String> productoIds;

    public Servicio() {
    }

    public Servicio(String id, String nombre, String descripcion, Double precio, int duracionMin,
                    String imagenUrl, boolean activo, String tipoServicioId, List<String> productoIds) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.duracionMin = duracionMin;
        this.imagenUrl = imagenUrl;
        this.activo = activo;
        this.tipoServicioId = tipoServicioId;
        this.productoIds = productoIds;
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getDuracionMin() {
        return duracionMin;
    }

    public void setDuracionMin(int duracionMin) {
        this.duracionMin = duracionMin;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getTipoServicioId() {
        return tipoServicioId;
    }

    public void setTipoServicioId(String tipoServicioId) {
        this.tipoServicioId = tipoServicioId;
    }

    public List<String> getProductoIds() {
        return productoIds;
    }

    public void setProductoIds(List<String> productoIds) {
        this.productoIds = productoIds;
    }

    @Override
    public String toString() {
        return "Servicio{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", duracionMin=" + duracionMin +
                ", imagenUrl='" + imagenUrl + '\'' +
                ", activo=" + activo +
                ", tipoServicioId='" + tipoServicioId + '\'' +
                ", productoIds=" + productoIds +
                '}';
    }
}
