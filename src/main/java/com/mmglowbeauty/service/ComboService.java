package com.mmglowbeauty.service;

import com.mmglowbeauty.dto.ComboRequest;

import java.util.List;

public interface ComboService {
    ComboRequest crearCombo(ComboRequest request);
    void actualizarCombo(String id, ComboRequest request);
    void eliminarCombo(String id);
    List<ComboRequest> listarCombos();
}
