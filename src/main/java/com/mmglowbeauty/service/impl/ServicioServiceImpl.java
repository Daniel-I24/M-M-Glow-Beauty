package com.mmglowbeauty.service.impl;

import com.mmglowbeauty.dto.ServicioRequest;
import com.mmglowbeauty.exception.RecursoNoEncontradoException;
import com.mmglowbeauty.model.Servicio;
import com.mmglowbeauty.repository.ServicioRepository;
import com.mmglowbeauty.service.ServicioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ServicioServiceImpl implements ServicioService {

    private static final Logger log = LoggerFactory.getLogger(ServicioServiceImpl.class);

    private final ServicioRepository servicioRepository;

    public ServicioServiceImpl(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    @Override
    public ServicioRequest crearServicio(ServicioRequest request) {
        log.info("Creando servicio: {}", request.nombre);

        Servicio servicio = new Servicio();
        servicio.setNombre(request.nombre);
        servicio.setDescripcion(request.descripcion);
        servicio.setPrecio(request.precio);
        servicio.setDuracionMin(request.duracionMin);
        servicio.setTipoServicioId(request.tipoServicioId);
        servicio.setImagenUrl(request.imagenUrl);
        servicio.setProductoIds(request.productoIds != null ? request.productoIds : new ArrayList<>());
        servicio.setActivo(true);

        servicioRepository.save(servicio);
        log.info("Servicio creado exitosamente: {}", servicio.getId());

        return request;
    }

    @Override
    public void actualizarServicio(String id, ServicioRequest request) {
        log.info("Actualizando servicio con id: {}", id);

        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Servicio no encontrado con id: " + id));

        servicio.setNombre(request.nombre);
        servicio.setDescripcion(request.descripcion);
        servicio.setPrecio(request.precio);
        servicio.setDuracionMin(request.duracionMin);
        servicio.setTipoServicioId(request.tipoServicioId);
        servicio.setImagenUrl(request.imagenUrl);
        servicio.setProductoIds(request.productoIds != null ? request.productoIds : new ArrayList<>());

        servicioRepository.save(servicio);
        log.info("Servicio actualizado exitosamente: {}", id);
    }

    @Override
    public void eliminarServicio(String id) {
        log.info("Eliminando servicio con id: {}", id);

        if (!servicioRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Servicio no encontrado con id: " + id);
        }

        servicioRepository.deleteById(id);
        log.info("Servicio eliminado exitosamente: {}", id);
    }

    @Override
    public List<ServicioRequest> listarServicios() {
        log.info("Listando servicios");
        List<Servicio> servicios = servicioRepository.findAll();
        List<ServicioRequest> requests = new ArrayList<>();

        for (Servicio servicio : servicios) {
            ServicioRequest req = new ServicioRequest();
            req.nombre = servicio.getNombre();
            req.descripcion = servicio.getDescripcion();
            req.precio = servicio.getPrecio();
            req.duracionMin = servicio.getDuracionMin();
            req.tipoServicioId = servicio.getTipoServicioId();
            req.imagenUrl = servicio.getImagenUrl();
            req.productoIds = servicio.getProductoIds();
            requests.add(req);
        }

        return requests;
    }

}
