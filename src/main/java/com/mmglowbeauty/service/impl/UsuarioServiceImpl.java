package com.mmglowbeauty.service.impl;

import com.mmglowbeauty.dto.UsuarioRequest;
import com.mmglowbeauty.exception.RecursoNoEncontradoException;
import com.mmglowbeauty.model.Administradora;
import com.mmglowbeauty.model.Cliente;
import com.mmglowbeauty.model.Empleada;
import com.mmglowbeauty.model.Usuario;
import com.mmglowbeauty.model.enums.RolUsuario;
import com.mmglowbeauty.repository.UsuarioRepository;
import com.mmglowbeauty.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UsuarioRequest crearUsuario(UsuarioRequest request) {
        log.info("Creando usuario: {}", request.correo);

        String contrasenaEncriptada = passwordEncoder.encode(request.contrasena);
        RolUsuario rol = RolUsuario.valueOf(request.rol);
        LocalDateTime fechaRegistro = LocalDateTime.now();

        Usuario usuario;

        switch (rol) {
            case CLIENTE:
                Cliente cliente = new Cliente();
                cliente.setNombre(request.nombre);
                cliente.setCorreo(request.correo);
                cliente.setTelefono(request.telefono);
                cliente.setContrasena(contrasenaEncriptada);
                cliente.setRol(RolUsuario.CLIENTE);
                cliente.setFechaRegistro(fechaRegistro);
                cliente.setDireccion(request.direccion);
                cliente.setFechaNacimiento(request.fechaNacimiento);
                cliente.setPreferencias(request.preferencias);
                usuario = cliente;
                break;

            case EMPLEADA:
                Empleada empleada = new Empleada();
                empleada.setNombre(request.nombre);
                empleada.setCorreo(request.correo);
                empleada.setTelefono(request.telefono);
                empleada.setContrasena(contrasenaEncriptada);
                empleada.setRol(RolUsuario.EMPLEADA);
                empleada.setFechaRegistro(fechaRegistro);
                empleada.setEspecialidad(request.especialidad);
                empleada.setTurno(request.turno);
                empleada.setCodigoEmpleada(request.codigoEmpleada);
                usuario = empleada;
                break;

            case ADMINISTRADORA:
                Administradora administradora = new Administradora();
                administradora.setNombre(request.nombre);
                administradora.setCorreo(request.correo);
                administradora.setTelefono(request.telefono);
                administradora.setContrasena(contrasenaEncriptada);
                administradora.setRol(RolUsuario.ADMINISTRADORA);
                administradora.setFechaRegistro(fechaRegistro);
                administradora.setCodigoAdmin(request.codigoAdmin);
                usuario = administradora;
                break;

            default:
                throw new IllegalArgumentException("Rol no válido: " + request.rol);
        }

        usuarioRepository.save(usuario);
        log.info("Usuario creado exitosamente: {}", usuario.getCorreo());

        return request;
    }

    @Override
    public void actualizarUsuario(String id, UsuarioRequest request) {
        log.info("Actualizando usuario con id: {}", id);

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id: " + id));

        usuario.setNombre(request.nombre);
        usuario.setCorreo(request.correo);
        usuario.setTelefono(request.telefono);

        if (request.contrasena != null && !request.contrasena.isEmpty()) {
            usuario.setContrasena(passwordEncoder.encode(request.contrasena));
        }

        if (usuario instanceof Cliente) {
            Cliente cliente = (Cliente) usuario;
            cliente.setDireccion(request.direccion);
            cliente.setFechaNacimiento(request.fechaNacimiento);
            cliente.setPreferencias(request.preferencias);
        } else if (usuario instanceof Empleada) {
            Empleada empleada = (Empleada) usuario;
            empleada.setEspecialidad(request.especialidad);
            empleada.setTurno(request.turno);
            empleada.setCodigoEmpleada(request.codigoEmpleada);
        } else if (usuario instanceof Administradora) {
            Administradora administradora = (Administradora) usuario;
            administradora.setCodigoAdmin(request.codigoAdmin);
        }

        usuario.actualizarPerfil();
        usuarioRepository.save(usuario);
        log.info("Usuario actualizado exitosamente: {}", id);
    }

    @Override
    public void eliminarUsuario(String id) {
        log.info("Eliminando usuario con id: {}", id);

        if (!usuarioRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Usuario no encontrado con id: " + id);
        }

        usuarioRepository.deleteById(id);
        log.info("Usuario eliminado exitosamente: {}", id);
    }

    @Override
    public List<UsuarioRequest> listarUsuarios() {
        log.info("Listando usuarios");
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioRequest> requests = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            UsuarioRequest req = new UsuarioRequest();
            req.nombre = usuario.getNombre();
            req.correo = usuario.getCorreo();
            req.telefono = usuario.getTelefono();
            req.rol = usuario.getRol().name();

            if (usuario instanceof Cliente) {
                Cliente cliente = (Cliente) usuario;
                req.direccion = cliente.getDireccion();
                req.fechaNacimiento = cliente.getFechaNacimiento();
                req.preferencias = cliente.getPreferencias();
            } else if (usuario instanceof Empleada) {
                Empleada empleada = (Empleada) usuario;
                req.especialidad = empleada.getEspecialidad();
                req.turno = empleada.getTurno();
                req.codigoEmpleada = empleada.getCodigoEmpleada();
            } else if (usuario instanceof Administradora) {
                Administradora admin = (Administradora) usuario;
                req.codigoAdmin = admin.getCodigoAdmin();
            }

            requests.add(req);
        }

        return requests;
    }
}
