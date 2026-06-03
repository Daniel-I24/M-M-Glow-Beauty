package com.mmglowbeauty.dto;

public class ProductoResponse {

    public String id;
    public String nombre;
    public String descripcion;
    public int cantidad;
    public int cantidadMinima;
    public Double precioUnitario;
    public String proveedor;

    public ProductoResponse() {
    }

    public ProductoResponse(String id, String nombre, String descripcion, int cantidad, int cantidadMinima,
                          Double precioUnitario, String proveedor) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.cantidadMinima = cantidadMinima;
        this.precioUnitario = precioUnitario;
        this.proveedor = proveedor;
    }
}
