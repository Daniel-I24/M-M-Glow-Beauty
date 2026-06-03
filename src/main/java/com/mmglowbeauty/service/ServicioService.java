package com.mmglowbeauty.service;

import com.mmglowbeauty.dto.ServicioRequest;

import java.util.List;

public interface ServicioService {
    ServicioRequest crearServicio(ServicioRequest request);
    void actualizarServicio(String id, ServicioRequest request);
    void eliminarServicio(String id);
    List<ServicioRequest> listarServicios();
}
