package com.mmglowbeauty.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "recordatorios")
public class Recordatorio {

    @Id
    private String id;
    private String citaId;
    private String mensaje;
    private String canal;
    private LocalDateTime fechaEnvio;
    private String estado;

    public Recordatorio() {
    }

    public Recordatorio(String id, String citaId, String mensaje, String canal,
                        LocalDateTime fechaEnvio, String estado) {
        this.id = id;
        this.citaId = citaId;
        this.mensaje = mensaje;
        this.canal = canal;
        this.fechaEnvio = fechaEnvio;
        this.estado = estado;
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

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Recordatorio{" +
                "id='" + id + '\'' +
                ", citaId='" + citaId + '\'' +
                ", mensaje='" + mensaje + '\'' +
                ", canal='" + canal + '\'' +
                ", fechaEnvio=" + fechaEnvio +
                ", estado='" + estado + '\'' +
                '}';
    }
}
