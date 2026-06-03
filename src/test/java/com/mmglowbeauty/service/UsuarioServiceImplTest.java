package com.mmglowbeauty.service;

import com.mmglowbeauty.dto.UsuarioRequest;
import com.mmglowbeauty.exception.RecursoNoEncontradoException;
import com.mmglowbeauty.model.Cliente;
import com.mmglowbeauty.model.Usuario;
import com.mmglowbeauty.model.enums.RolUsuario;
import com.mmglowbeauty.repository.UsuarioRepository;
import com.mmglowbeauty.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private UsuarioRequest clienteRequest;

    @BeforeEach
    void setUp() {
        clienteRequest = new UsuarioRequest();
        clienteRequest.nombre = "Juan Pérez";
        clienteRequest.correo = "juan@example.com";
        clienteRequest.telefono = "3001234567";
        clienteRequest.contrasena = "password123";
        clienteRequest.rol = "CLIENTE";
        clienteRequest.direccion = "Calle 1 #2-3";
    }

    @Test
    void testCrearCliente_exitoso() {
        when(passwordEncoder.encode(any())).thenReturn("$2a$10$encodedPassword");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(i -> i.getArguments()[0]);

        UsuarioRequest result = usuarioService.crearUsuario(clienteRequest);

        assertNotNull(result);
        assertEquals("Juan Pérez", result.nombre);
        verify(passwordEncoder, times(1)).encode("password123");
        verify(usuarioRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void testEliminarUsuario_noEncontrado() {
        when(usuarioRepository.existsById("123")).thenReturn(false);

        assertThrows(RecursoNoEncontradoException.class, () -> {
            usuarioService.eliminarUsuario("123");
        });

        verify(usuarioRepository, never()).deleteById(any());
    }

    @Test
    void testListarUsuarios_retornaLista() {
        when(usuarioRepository.findAll()).thenReturn(java.util.List.of());

        var result = usuarioService.listarUsuarios();

        assertNotNull(result);
        verify(usuarioRepository, times(1)).findAll();
    }
}
