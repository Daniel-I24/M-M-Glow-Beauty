package com.mmglowbeauty.service;

import com.mmglowbeauty.dto.PromocionRequest;

import java.util.List;

public interface PromocionService {
    void crearPromocion(PromocionRequest request);
    boolean validarVigencia(String id);
    List<PromocionRequest> listarPromociones();
}
