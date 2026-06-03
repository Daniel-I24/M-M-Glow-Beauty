package com.mmglowbeauty.service;

import com.mmglowbeauty.dto.PagoRequest;

import java.util.List;

public interface PagoService {
    void registrarPago(PagoRequest request);
    byte[] generarComprobante(String id);
    List<PagoRequest> listarPagos();
}
