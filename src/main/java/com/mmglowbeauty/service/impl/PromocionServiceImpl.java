package com.mmglowbeauty.service.impl;

import com.mmglowbeauty.dto.PromocionRequest;
import com.mmglowbeauty.exception.RecursoNoEncontradoException;
import com.mmglowbeauty.model.Promocion;
import com.mmglowbeauty.repository.PromocionRepository;
import com.mmglowbeauty.service.PromocionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PromocionServiceImpl implements PromocionService {

    private static final Logger log = LoggerFactory.getLogger(PromocionServiceImpl.class);

    private final PromocionRepository promocionRepository;

    public PromocionServiceImpl(PromocionRepository promocionRepository) {
        this.promocionRepository = promocionRepository;
    }

    @Override
    public void crearPromocion(PromocionRequest request) {
        log.info("Creando promoción: {}", request.titulo);

        Promocion promocion = new Promocion();
        promocion.setTitulo(request.titulo);
        promocion.setDescripcion(request.descripcion);
        promocion.setDescuento(request.descuento);
        promocion.setFechaInicio(request.fechaInicio);
        promocion.setFechaFin(request.fechaFin);
        promocion.setAnuncioId(request.anuncioId);

        LocalDate hoy = LocalDate.now();
        boolean activa = !hoy.isBefore(request.fechaInicio) && !hoy.isAfter(request.fechaFin);
        promocion.setActiva(activa);

        promocionRepository.save(promocion);
        log.info("Promoción creada exitosamente: {}", promocion.getId());
    }

    @Override
    public boolean validarVigencia(String id) {
        Promocion promocion = promocionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Promoción no encontrada con id: " + id));

        LocalDate hoy = LocalDate.now();
        return promocion.isActiva() &&
               !hoy.isBefore(promocion.getFechaInicio()) &&
               !hoy.isAfter(promocion.getFechaFin());
    }

    @Override
    public List<PromocionRequest> listarPromociones() {
        log.info("Listando promociones");
        List<Promocion> promociones = promocionRepository.findAll();
        List<PromocionRequest> requests = new ArrayList<>();

        for (Promocion promocion : promociones) {
            PromocionRequest req = new PromocionRequest();
            req.titulo = promocion.getTitulo();
            req.descripcion = promocion.getDescripcion();
            req.descuento = promocion.getDescuento();
            req.fechaInicio = promocion.getFechaInicio();
            req.fechaFin = promocion.getFechaFin();
            req.anuncioId = promocion.getAnuncioId();
            requests.add(req);
        }

        return requests;
    }
}
