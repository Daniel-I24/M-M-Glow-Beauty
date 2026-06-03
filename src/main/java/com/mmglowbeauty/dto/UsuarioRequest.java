package com.mmglowbeauty.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public class UsuarioRequest {

    @NotBlank(message = "El nombre es obligatorio")
    public String nombre;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe ser válido")
    public String correo;

    @NotBlank(message = "El teléfono es obligatorio")
    public String telefono;

    @NotBlank(message = "La contraseña es obligatoria")
    public String contrasena;

    @NotBlank(message = "El rol es obligatorio")
    public String rol;

    // Campos específicos para CLIENTE
    public String direccion;
    public LocalDate fechaNacimiento;
    public String preferencias;

    // Campos específicos para EMPLEADA
    public String especialidad;
    public String turno;
    public String codigoEmpleada;

    // Campos específicos para ADMINISTRADORA
    public String codigoAdmin;

    public UsuarioRequest() {
    }

    public UsuarioRequest(String nombre, String correo, String telefono, String contrasena, String rol,
                          String direccion, LocalDate fechaNacimiento, String preferencias,
                          String especialidad, String turno, String codigoEmpleada, String codigoAdmin) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.rol = rol;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.preferencias = preferencias;
        this.especialidad = especialidad;
        this.turno = turno;
        this.codigoEmpleada = codigoEmpleada;
        this.codigoAdmin = codigoAdmin;
    }
}
