package com.mmglowbeauty.model;

import com.mmglowbeauty.model.enums.EstadoCita;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "citas")
public class Cita {

    @Id
    private String id;
    private LocalDateTime fechaHora;
    private EstadoCita estado;
    private String notas;
    private int duracionMin;
    private String clienteId;
    private String empleadaId;
    private List<String> servicioIds;
    private String pagoId;
    private List<String> productosConsumidosIds;

    public Cita() {
    }

    public Cita(String id, LocalDateTime fechaHora, EstadoCita estado, String notas, int duracionMin,
                String clienteId, String empleadaId, List<String> servicioIds, String pagoId,
                List<String> productosConsumidosIds) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.notas = notas;
        this.duracionMin = duracionMin;
        this.clienteId = clienteId;
        this.empleadaId = empleadaId;
        this.servicioIds = servicioIds;
        this.pagoId = pagoId;
        this.productosConsumidosIds = productosConsumidosIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public int getDuracionMin() {
        return duracionMin;
    }

    public void setDuracionMin(int duracionMin) {
        this.duracionMin = duracionMin;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getEmpleadaId() {
        return empleadaId;
    }

    public void setEmpleadaId(String empleadaId) {
        this.empleadaId = empleadaId;
    }

    public List<String> getServicioIds() {
        return servicioIds;
    }

    public void setServicioIds(List<String> servicioIds) {
        this.servicioIds = servicioIds;
    }

    public String getPagoId() {
        return pagoId;
    }

    public void setPagoId(String pagoId) {
        this.pagoId = pagoId;
    }

    public List<String> getProductosConsumidosIds() {
        return productosConsumidosIds;
    }

    public void setProductosConsumidosIds(List<String> productosConsumidosIds) {
        this.productosConsumidosIds = productosConsumidosIds;
    }

    @Override
    public String toString() {
        return "Cita{" +
                "id='" + id + '\'' +
                ", fechaHora=" + fechaHora +
                ", estado=" + estado +
                ", notas='" + notas + '\'' +
                ", duracionMin=" + duracionMin +
                ", clienteId='" + clienteId + '\'' +
                ", empleadaId='" + empleadaId + '\'' +
                ", servicioIds=" + servicioIds +
                ", pagoId='" + pagoId + '\'' +
                ", productosConsumidosIds=" + productosConsumidosIds +
                '}';
    }
}
