package com.mmglowbeauty.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "calificaciones")
public class Calificacion {

    @Id
    private String id;
    private String citaId;
    private String clienteId;
    private int puntuacion;
    private String comentario;
    private LocalDate fecha;
    private String respuesta;

    public Calificacion() {
    }

    public Calificacion(String id, String citaId, String clienteId, int puntuacion, String comentario,
                        LocalDate fecha, String respuesta) {
        this.id = id;
        this.citaId = citaId;
        this.clienteId = clienteId;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
        this.fecha = fecha;
        this.respuesta = respuesta;
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

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    @Override
    public String toString() {
        return "Calificacion{" +
                "id='" + id + '\'' +
                ", citaId='" + citaId + '\'' +
                ", clienteId='" + clienteId + '\'' +
                ", puntuacion=" + puntuacion +
                ", comentario='" + comentario + '\'' +
                ", fecha=" + fecha +
                ", respuesta='" + respuesta + '\'' +
                '}';
    }
}
