package com.mmglowbeauty.service;

import com.mmglowbeauty.dto.UsuarioRequest;

import java.util.List;

public interface UsuarioService {
    UsuarioRequest crearUsuario(UsuarioRequest request);
    void actualizarUsuario(String id, UsuarioRequest request);
    void eliminarUsuario(String id);
    List<UsuarioRequest> listarUsuarios();
}
