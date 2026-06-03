package com.mmglowbeauty.model;

import com.mmglowbeauty.model.enums.RolUsuario;
import org.springframework.data.annotation.TypeAlias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@TypeAlias("EMPLEADA")
public class Empleada extends Usuario {

    private static final Logger log = LoggerFactory.getLogger(Empleada.class);

    private String especialidad;
    private String turno;
    private String codigoEmpleada;

    public Empleada() {
        super();
    }

    public Empleada(String id, String nombre, String correo, String telefono, String contrasena,
                    RolUsuario rol, LocalDateTime fechaRegistro, String especialidad,
                    String turno, String codigoEmpleada) {
        super(id, nombre, correo, telefono, contrasena, rol, fechaRegistro);
        this.especialidad = especialidad;
        this.turno = turno;
        this.codigoEmpleada = codigoEmpleada;
    }

    @Override
    public void actualizarPerfil() {
        log.info("Perfil de empleada actualizado: {}", this.getCorreo());
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getCodigoEmpleada() {
        return codigoEmpleada;
    }

    public void setCodigoEmpleada(String codigoEmpleada) {
        this.codigoEmpleada = codigoEmpleada;
    }

    @Override
    public String toString() {
        return "Empleada{" +
                "id='" + getId() + '\'' +
                ", nombre='" + getNombre() + '\'' +
                ", correo='" + getCorreo() + '\'' +
                ", telefono='" + getTelefono() + '\'' +
                ", rol=" + getRol() +
                ", especialidad='" + especialidad + '\'' +
                ", turno='" + turno + '\'' +
                ", codigoEmpleada='" + codigoEmpleada + '\'' +
                '}';
    }
}
