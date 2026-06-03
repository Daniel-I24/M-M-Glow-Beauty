package com.mmglowbeauty.service.impl;

import com.mmglowbeauty.dto.ComboRequest;
import com.mmglowbeauty.exception.RecursoNoEncontradoException;
import com.mmglowbeauty.model.Combo;
import com.mmglowbeauty.repository.ComboRepository;
import com.mmglowbeauty.service.ComboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ComboServiceImpl implements ComboService {

    private static final Logger log = LoggerFactory.getLogger(ComboServiceImpl.class);

    private final ComboRepository comboRepository;

    public ComboServiceImpl(ComboRepository comboRepository) {
        this.comboRepository = comboRepository;
    }

    @Override
    public ComboRequest crearCombo(ComboRequest request) {
        log.info("Creando combo: {}", request.nombre);

        Combo combo = new Combo();
        combo.setNombre(request.nombre);
        combo.setDescripcion(request.descripcion);
        combo.setPrecioCombo(request.precioCombo);
        combo.setDescuento(request.descuento);
        combo.setServicioIds(request.servicioIds);
        combo.setAnuncioId(request.anuncioId);

        comboRepository.save(combo);
        log.info("Combo creado exitosamente: {}", combo.getId());

        return request;
    }

    @Override
    public void actualizarCombo(String id, ComboRequest request) {
        log.info("Actualizando combo con id: {}", id);

        Combo combo = comboRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Combo no encontrado con id: " + id));

        combo.setNombre(request.nombre);
        combo.setDescripcion(request.descripcion);
        combo.setPrecioCombo(request.precioCombo);
        combo.setDescuento(request.descuento);
        combo.setServicioIds(request.servicioIds);
        combo.setAnuncioId(request.anuncioId);

        comboRepository.save(combo);
        log.info("Combo actualizado exitosamente: {}", id);
    }

    @Override
    public void eliminarCombo(String id) {
        log.info("Eliminando combo con id: {}", id);

        if (!comboRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Combo no encontrado con id: " + id);
        }

        comboRepository.deleteById(id);
        log.info("Combo eliminado exitosamente: {}", id);
    }

    @Override
    public List<ComboRequest> listarCombos() {
        log.info("Listando combos");
        List<Combo> combos = comboRepository.findAll();
        List<ComboRequest> requests = new ArrayList<>();

        for (Combo combo : combos) {
            ComboRequest req = new ComboRequest();
            req.nombre = combo.getNombre();
            req.descripcion = combo.getDescripcion();
            req.precioCombo = combo.getPrecioCombo();
            req.descuento = combo.getDescuento();
            req.servicioIds = combo.getServicioIds();
            req.anuncioId = combo.getAnuncioId();
            requests.add(req);
        }

        return requests;
    }
}
