package com.mmglowbeauty.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mmglowbeauty.model.enums.RolUsuario;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "usuarios")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "_tipo")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Cliente.class, name = "CLIENTE"),
    @JsonSubTypes.Type(value = Empleada.class, name = "EMPLEADA"),
    @JsonSubTypes.Type(value = Administradora.class, name = "ADMINISTRADORA")
})
public abstract class Usuario {

    @Id
    private String id;
    private String nombre;
    private String correo;
    private String telefono;
    private String contrasena;
    private RolUsuario rol;
    private LocalDateTime fechaRegistro;

    public Usuario() {
    }

    public Usuario(String id, String nombre, String correo, String telefono, String contrasena, RolUsuario rol, LocalDateTime fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.rol = rol;
        this.fechaRegistro = fechaRegistro;
    }

    public abstract void actualizarPerfil();

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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public RolUsuario getRol() {
        return rol;
    }

    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", rol=" + rol +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
}
