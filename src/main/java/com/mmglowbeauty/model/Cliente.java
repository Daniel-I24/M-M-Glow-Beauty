package com.mmglowbeauty.model;

import com.mmglowbeauty.model.enums.RolUsuario;
import org.springframework.data.annotation.TypeAlias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;

@TypeAlias("CLIENTE")
public class Cliente extends Usuario {

    private static final Logger log = LoggerFactory.getLogger(Cliente.class);

    private String direccion;
    private LocalDate fechaNacimiento;
    private String preferencias;

    public Cliente() {
        super();
    }

    public Cliente(String id, String nombre, String correo, String telefono, String contrasena, 
                   RolUsuario rol, LocalDateTime fechaRegistro, String direccion, 
                   LocalDate fechaNacimiento, String preferencias) {
        super(id, nombre, correo, telefono, contrasena, rol, fechaRegistro);
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.preferencias = preferencias;
    }

    @Override
    public void actualizarPerfil() {
        log.info("Perfil de cliente actualizado: {}", this.getCorreo());
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(String preferencias) {
        this.preferencias = preferencias;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id='" + getId() + '\'' +
                ", nombre='" + getNombre() + '\'' +
                ", correo='" + getCorreo() + '\'' +
                ", telefono='" + getTelefono() + '\'' +
                ", rol=" + getRol() +
                ", direccion='" + direccion + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", preferencias='" + preferencias + '\'' +
                '}';
    }
}
