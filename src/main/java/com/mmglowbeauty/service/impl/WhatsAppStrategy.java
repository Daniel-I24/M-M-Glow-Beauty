package com.mmglowbeauty.service.impl;

import com.mmglowbeauty.service.NotificacionStrategy;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
@ConditionalOnProperty(name = "notificacion.canal", havingValue = "WHATSAPP")
@Primary
public class WhatsAppStrategy implements NotificacionStrategy {

    private static final Logger log = LoggerFactory.getLogger(WhatsAppStrategy.class);

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.from.number}")
    private String fromNumber;

    @PostConstruct
    public void init() {
        if (accountSid != null && !accountSid.isEmpty() && authToken != null && !authToken.isEmpty()) {
            Twilio.init(accountSid, authToken);
            log.info("Twilio inicializado correctamente");
        } else {
            log.warn("Credenciales de Twilio no configuradas");
        }
    }

    @Override
    public void enviar(String destinatario, String mensaje) {
        try {
            Message message = Message.creator(
                    new PhoneNumber("whatsapp:" + destinatario),
                    new PhoneNumber(fromNumber),
                    mensaje
            ).create();
            
            log.info("WhatsApp enviado exitosamente a: {}, SID: {}", destinatario, message.getSid());
        } catch (Exception e) {
            log.error("Error al enviar WhatsApp a {}: {}", destinatario, e.getMessage());
        }
    }
}
