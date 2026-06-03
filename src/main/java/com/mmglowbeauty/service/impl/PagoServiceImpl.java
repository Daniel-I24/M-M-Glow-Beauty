package com.mmglowbeauty.service.impl;

import com.mmglowbeauty.dto.PagoRequest;
import com.mmglowbeauty.model.Pago;
import com.mmglowbeauty.model.enums.MetodoPago;
import com.mmglowbeauty.repository.PagoRepository;
import com.mmglowbeauty.service.PagoService;
import com.mmglowbeauty.exception.RecursoNoEncontradoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PagoServiceImpl implements PagoService {

    private static final Logger log = LoggerFactory.getLogger(PagoServiceImpl.class);

    private final PagoRepository pagoRepository;

    public PagoServiceImpl(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    @Override
    public void registrarPago(PagoRequest request) {
        log.info("Registrando pago para cita: {}", request.citaId);

        Pago pago = new Pago();
        pago.setCitaId(request.citaId);
        pago.setMonto(request.monto);
        pago.setMetodoPago(MetodoPago.valueOf(request.metodoPago));
        pago.setEstado("COMPLETADO");
        pago.setFecha(LocalDateTime.now());
        pago.setComprobante(request.comprobante != null ? request.comprobante : "Sin comprobante");

        pagoRepository.save(pago);
        log.info("Pago registrado exitosamente: {}", pago.getId());
    }

    @Override
    public byte[] generarComprobante(String id) {
        log.info("Generando comprobante para pago: {}", id);

        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pago no encontrado con id: " + id));

        StringBuilder comprobante = new StringBuilder();
        comprobante.append("========================================\n");
        comprobante.append("       M&M GLOW BEAUTY\n");
        comprobante.append("    COMPROBANTE DE PAGO\n");
        comprobante.append("========================================\n\n");
        comprobante.append("ID Pago: ").append(pago.getId()).append("\n");
        comprobante.append("ID Cita: ").append(pago.getCitaId()).append("\n");
        comprobante.append("Fecha: ").append(pago.getFecha()).append("\n");
        comprobante.append("Monto: $").append(String.format("%.2f", pago.getMonto())).append("\n");
        comprobante.append("Método de Pago: ").append(pago.getMetodoPago()).append("\n");
        comprobante.append("Estado: ").append(pago.getEstado()).append("\n");
        comprobante.append("\n========================================\n");
        comprobante.append("Gracias por su preferencia\n");
        comprobante.append("========================================\n");

        return comprobante.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public List<PagoRequest> listarPagos() {
        log.info("Listando pagos");
        List<Pago> pagos = pagoRepository.findAll();
        List<PagoRequest> requests = new ArrayList<>();

        for (Pago pago : pagos) {
            PagoRequest req = new PagoRequest();
            req.citaId = pago.getCitaId();
            req.monto = pago.getMonto();
            req.metodoPago = pago.getMetodoPago().name();
            req.comprobante = pago.getComprobante();
            requests.add(req);
        }

        return requests;
    }
}
