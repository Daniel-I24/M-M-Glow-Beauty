package com.mmglowbeauty.model;

import com.mmglowbeauty.model.enums.RolUsuario;
import org.springframework.data.annotation.TypeAlias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@TypeAlias("ADMINISTRADORA")
public class Administradora extends Usuario {

    private static final Logger log = LoggerFactory.getLogger(Administradora.class);

    private String codigoAdmin;

    public Administradora() {
        super();
    }

    public Administradora(String id, String nombre, String correo, String telefono, String contrasena,
                          RolUsuario rol, LocalDateTime fechaRegistro, String codigoAdmin) {
        super(id, nombre, correo, telefono, contrasena, rol, fechaRegistro);
        this.codigoAdmin = codigoAdmin;
    }

    @Override
    public void actualizarPerfil() {
        log.info("Perfil de administradora actualizado: {}", this.getCorreo());
    }

    public String getCodigoAdmin() {
        return codigoAdmin;
    }

    public void setCodigoAdmin(String codigoAdmin) {
        this.codigoAdmin = codigoAdmin;
    }

    @Override
    public String toString() {
        return "Administradora{" +
                "id='" + getId() + '\'' +
                ", nombre='" + getNombre() + '\'' +
                ", correo='" + getCorreo() + '\'' +
                ", telefono='" + getTelefono() + '\'' +
                ", rol=" + getRol() +
                ", codigoAdmin='" + codigoAdmin + '\'' +
                '}';
    }
}
