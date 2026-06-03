package com.mmglowbeauty.service.impl;

import com.mmglowbeauty.dto.AnuncioRequest;
import com.mmglowbeauty.exception.RecursoNoEncontradoException;
import com.mmglowbeauty.model.Anuncio;
import com.mmglowbeauty.model.enums.TipoAnuncio;
import com.mmglowbeauty.repository.AnuncioRepository;
import com.mmglowbeauty.service.AnuncioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AnuncioServiceImpl implements AnuncioService {

    private static final Logger log = LoggerFactory.getLogger(AnuncioServiceImpl.class);

    private final AnuncioRepository anuncioRepository;

    public AnuncioServiceImpl(AnuncioRepository anuncioRepository) {
        this.anuncioRepository = anuncioRepository;
    }

    @Override
    public AnuncioRequest crearAnuncio(AnuncioRequest request) {
        log.info("Creando anuncio: {}", request.titulo);

        Anuncio anuncio = new Anuncio();
        anuncio.setTitulo(request.titulo);
        anuncio.setDescripcion(request.descripcion);
        anuncio.setImagenUrl(request.imagenUrl);
        anuncio.setTipo(TipoAnuncio.valueOf(request.tipo));
        anuncio.setFechaInicio(request.fechaInicio);
        anuncio.setFechaFin(request.fechaFin);
        anuncio.setActivo(true);

        anuncioRepository.save(anuncio);
        log.info("Anuncio creado exitosamente: {}", anuncio.getId());

        return request;
    }

    @Override
    public void activarAnuncio(String id) {
        log.info("Activando anuncio con id: {}", id);

        Anuncio anuncio = anuncioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Anuncio no encontrado con id: " + id));

        anuncio.setActivo(true);
        anuncioRepository.save(anuncio);
        log.info("Anuncio activado exitosamente: {}", id);
    }

    @Override
    public void desactivarAnuncio(String id) {
        log.info("Desactivando anuncio con id: {}", id);

        Anuncio anuncio = anuncioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Anuncio no encontrado con id: " + id));

        anuncio.setActivo(false);
        anuncioRepository.save(anuncio);
        log.info("Anuncio desactivado exitosamente: {}", id);
    }

    @Override
    public List<AnuncioRequest> listarAnunciosActivos() {
        log.info("Listando anuncios activos");
        List<Anuncio> anuncios = anuncioRepository.findByActivoTrue();
        List<AnuncioRequest> requests = new ArrayList<>();

        for (Anuncio anuncio : anuncios) {
            AnuncioRequest req = new AnuncioRequest();
            req.titulo = anuncio.getTitulo();
            req.descripcion = anuncio.getDescripcion();
            req.imagenUrl = anuncio.getImagenUrl();
            req.tipo = anuncio.getTipo().name();
            req.fechaInicio = anuncio.getFechaInicio();
            req.fechaFin = anuncio.getFechaFin();
            requests.add(req);
        }

        return requests;
    }
}
