package com.mmglowbeauty.model;

import com.mmglowbeauty.model.enums.MetodoPago;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "pagos")
public class Pago {

    @Id
    private String id;
    private String citaId;
    private Double monto;
    private MetodoPago metodoPago;
    private String estado;
    private LocalDateTime fecha;
    private String comprobante;

    public Pago() {
    }

    public Pago(String id, String citaId, Double monto, MetodoPago metodoPago, String estado,
                LocalDateTime fecha, String comprobante) {
        this.id = id;
        this.citaId = citaId;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.estado = estado;
        this.fecha = fecha;
        this.comprobante = comprobante;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCitaId() {
        return citaId;
    }

    public void setCitaId(String citaId) {
        this.citaId = citaId;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

    @Override
    public String toString() {
        return "Pago{" +
                "id='" + id + '\'' +
                ", citaId='" + citaId + '\'' +
                ", monto=" + monto +
                ", metodoPago=" + metodoPago +
                ", estado='" + estado + '\'' +
                ", fecha=" + fecha +
                ", comprobante='" + comprobante + '\'' +
                '}';
    }
}
