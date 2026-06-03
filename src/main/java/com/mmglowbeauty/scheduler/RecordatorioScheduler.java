package com.mmglowbeauty.scheduler;

import com.mmglowbeauty.model.Cita;
import com.mmglowbeauty.model.Recordatorio;
import com.mmglowbeauty.model.Usuario;
import com.mmglowbeauty.model.enums.EstadoCita;
import com.mmglowbeauty.repository.CitaRepository;
import com.mmglowbeauty.repository.RecordatorioRepository;
import com.mmglowbeauty.repository.UsuarioRepository;
import com.mmglowbeauty.service.NotificacionStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class RecordatorioScheduler {

    private static final Logger log = LoggerFactory.getLogger(RecordatorioScheduler.class);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

    private final CitaRepository citaRepository;
    private final RecordatorioRepository recordatorioRepository;
    private final UsuarioRepository usuarioRepository;
    private final NotificacionStrategy notificacionStrategy;

    @Value("${notificacion.canal}")
    private String canal;

    public RecordatorioScheduler(CitaRepository citaRepository, RecordatorioRepository recordatorioRepository,
                                 UsuarioRepository usuarioRepository, NotificacionStrategy notificacionStrategy) {
        this.citaRepository = citaRepository;
        this.recordatorioRepository = recordatorioRepository;
        this.usuarioRepository = usuarioRepository;
        this.notificacionStrategy = notificacionStrategy;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void procesarRecordatorios() {
        log.info("Iniciando procesamiento de recordatorios automáticos");
        LocalDateTime ahora = LocalDateTime.now();
        int recordatoriosEnviados = 0;

        // RECORDATORIOS DE 24 HORAS
        LocalDateTime inicio24h = ahora.plusHours(23);
        LocalDateTime fin24h = ahora.plusHours(25);

        List<Cita> citas24h = citaRepository.findByFechaHoraBetween(inicio24h, fin24h).stream()
                .filter(c -> c.getEstado() == EstadoCita.CONFIRMADA)
                .toList();

        for (Cita cita : citas24h) {
            String canalKey = "24H_" + canal;
            if (!recordatorioRepository.existsByCitaIdAndCanal(cita.getId(), canalKey)) {
                recordatoriosEnviados += enviarRecordatorio(cita, canalKey, "24 horas");
            }
        }

        // RECORDATORIOS DE 1 HORA
        LocalDateTime inicio1h = ahora.plusMinutes(50);
        LocalDateTime fin1h = ahora.plusMinutes(70);

        List<Cita> citas1h = citaRepository.findByFechaHoraBetween(inicio1h, fin1h).stream()
                .filter(c -> c.getEstado() == EstadoCita.CONFIRMADA)
                .toList();

        for (Cita cita : citas1h) {
            String canalKey = "1H_" + canal;
            if (!recordatorioRepository.existsByCitaIdAndCanal(cita.getId(), canalKey)) {
                recordatoriosEnviados += enviarRecordatorio(cita, canalKey, "1 hora");
            }
        }

        log.info("Procesamiento completado. Total de recordatorios enviados: {}", recordatoriosEnviados);
    }

    private int enviarRecordatorio(Cita cita, String canalKey, String tiempo) {
        try {
            Usuario cliente = usuarioRepository.findById(cita.getClienteId()).orElse(null);
            if (cliente == null) {
                log.warn("Cliente no encontrado para cita: {}", cita.getId());
                return 0;
            }

            String horaFormateada = cita.getFechaHora().format(FORMATTER);
            String mensaje = String.format("Recordatorio: tienes una cita en %s a las %s. ¡Te esperamos en M&M Glow Beauty!",
                    tiempo, horaFormateada);

            Recordatorio recordatorio = new Recordatorio();
            recordatorio.setCitaId(cita.getId());
            recordatorio.setMensaje(mensaje);
            recordatorio.setCanal(canalKey);
            recordatorio.setFechaEnvio(LocalDateTime.now());
            recordatorio.setEstado("PENDIENTE");
            recordatorioRepository.save(recordatorio);

            String destinatario = canal.equals("EMAIL") ? cliente.getCorreo() : cliente.getTelefono();
            notificacionStrategy.enviar(destinatario, mensaje);

            recordatorio.setEstado("ENVIADO");
            recordatorioRepository.save(recordatorio);

            log.info("Recordatorio de {} enviado para cita: {}", tiempo, cita.getId());
            return 1;

        } catch (Exception e) {
            log.error("Error al enviar recordatorio para cita {}: {}", cita.getId(), e.getMessage());

            Recordatorio recordatorioFallido = recordatorioRepository.findByCitaId(cita.getId()).stream()
                    .filter(r -> r.getCanal().equals(canalKey))
                    .findFirst()
                    .orElse(null);

            if (recordatorioFallido != null) {
                recordatorioFallido.setEstado("FALLIDO");
                recordatorioRepository.save(recordatorioFallido);
            }

            return 0;
        }
    }
}
