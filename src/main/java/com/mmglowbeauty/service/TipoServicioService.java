package com.mmglowbeauty.service;

import com.mmglowbeauty.dto.TipoServicioRequest;

import java.util.List;

public interface TipoServicioService {
    TipoServicioRequest crearTipoServicio(TipoServicioRequest request);
    void actualizarTipoServicio(String id, TipoServicioRequest request);
    void eliminarTipoServicio(String id);
    List<TipoServicioRequest> listarTiposServicio();
}
