package com.mmglowbeauty.service;

import com.mmglowbeauty.dto.AnuncioRequest;

import java.util.List;

public interface AnuncioService {
    AnuncioRequest crearAnuncio(AnuncioRequest request);
    void activarAnuncio(String id);
    void desactivarAnuncio(String id);
    List<AnuncioRequest> listarAnunciosActivos();
}
