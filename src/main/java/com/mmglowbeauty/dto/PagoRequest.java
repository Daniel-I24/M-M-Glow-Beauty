package com.mmglowbeauty.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PagoRequest {

    @NotBlank(message = "El ID de la cita es obligatorio")
    public String citaId;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser positivo")
    public Double monto;

    @NotBlank(message = "El método de pago es obligatorio")
    public String metodoPago;

    public String comprobante;

    public PagoRequest() {
    }

    public PagoRequest(String citaId, Double monto, String metodoPago, String comprobante) {
        this.citaId = citaId;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.comprobante = comprobante;
    }
}
