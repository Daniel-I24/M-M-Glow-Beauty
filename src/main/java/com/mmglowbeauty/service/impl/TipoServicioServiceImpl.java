package com.mmglowbeauty.service.impl;

import com.mmglowbeauty.dto.TipoServicioRequest;
import com.mmglowbeauty.exception.RecursoNoEncontradoException;
import com.mmglowbeauty.model.TipoServicio;
import com.mmglowbeauty.repository.TipoServicioRepository;
import com.mmglowbeauty.service.TipoServicioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TipoServicioServiceImpl implements TipoServicioService {

    private static final Logger log = LoggerFactory.getLogger(TipoServicioServiceImpl.class);

    private final TipoServicioRepository tipoServicioRepository;

    public TipoServicioServiceImpl(TipoServicioRepository tipoServicioRepository) {
        this.tipoServicioRepository = tipoServicioRepository;
    }

    @Override
    public TipoServicioRequest crearTipoServicio(TipoServicioRequest request) {
        log.info("Creando tipo de servicio: {}", request.nombre);

        TipoServicio tipoServicio = new TipoServicio();
        tipoServicio.setNombre(request.nombre);
        tipoServicio.setDescripcion(request.descripcion);

        tipoServicioRepository.save(tipoServicio);
        log.info("Tipo de servicio creado exitosamente: {}", tipoServicio.getId());

        return request;
    }

    @Override
    public void actualizarTipoServicio(String id, TipoServicioRequest request) {
        log.info("Actualizando tipo de servicio con id: {}", id);

        TipoServicio tipoServicio = tipoServicioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Tipo de servicio no encontrado con id: " + id));

        tipoServicio.setNombre(request.nombre);
        tipoServicio.setDescripcion(request.descripcion);

        tipoServicioRepository.save(tipoServicio);
        log.info("Tipo de servicio actualizado exitosamente: {}", id);
    }

    @Override
    public void eliminarTipoServicio(String id) {
        log.info("Eliminando tipo de servicio con id: {}", id);

        if (!tipoServicioRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Tipo de servicio no encontrado con id: " + id);
        }

        tipoServicioRepository.deleteById(id);
        log.info("Tipo de servicio eliminado exitosamente: {}", id);
    }

    @Override
    public List<TipoServicioRequest> listarTiposServicio() {
        log.info("Listando tipos de servicio");
        List<TipoServicio> tipos = tipoServicioRepository.findAll();
        List<TipoServicioRequest> requests = new ArrayList<>();

        for (TipoServicio tipo : tipos) {
            TipoServicioRequest req = new TipoServicioRequest();
            req.nombre = tipo.getNombre();
            req.descripcion = tipo.getDescripcion();
            requests.add(req);
        }

        return requests;
    }
}
