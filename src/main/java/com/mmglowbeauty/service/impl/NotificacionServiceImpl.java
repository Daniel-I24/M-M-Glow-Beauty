package com.mmglowbeauty.service.impl;

import com.mmglowbeauty.model.Usuario;
import com.mmglowbeauty.model.enums.RolUsuario;
import com.mmglowbeauty.repository.UsuarioRepository;
import com.mmglowbeauty.service.NotificacionService;
import com.mmglowbeauty.service.NotificacionStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NotificacionServiceImpl implements NotificacionService {

    private static final Logger log = LoggerFactory.getLogger(NotificacionServiceImpl.class);

    private final UsuarioRepository usuarioRepository;
    private final NotificacionStrategy notificacionStrategy;

    @Value("${notificacion.canal}")
    private String canal;

    public NotificacionServiceImpl(UsuarioRepository usuarioRepository, NotificacionStrategy notificacionStrategy) {
        this.usuarioRepository = usuarioRepository;
        this.notificacionStrategy = notificacionStrategy;
    }

    @Override
    public Map<String, Object> enviarNotificacionMasiva(String mensaje) {
        log.info("Enviando notificación masiva");

        List<Usuario> clientes = usuarioRepository.findByRol(RolUsuario.CLIENTE);
        int enviados = 0;
        int fallidos = 0;

        for (Usuario cliente : clientes) {
            try {
                String destinatario = canal.equals("EMAIL") ? cliente.getCorreo() : cliente.getTelefono();
                notificacionStrategy.enviar(destinatario, mensaje);
                enviados++;
            } catch (Exception e) {
                log.error("Error al enviar notificacion a {}: {}", cliente.getCorreo(), e.getMessage());
                fallidos++;
            }
        }

        log.info("Notificacion masiva completada. Enviados: {}, Fallidos: {}", enviados, fallidos);

        return Map.of(
                "totalClientes", clientes.size(),
                "enviados", enviados,
                "fallidos", fallidos,
                "mensaje", mensaje
        );
    }
}
