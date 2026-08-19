package com.bolsa.trabajos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CambiarEstadoRequest {

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "(?i)PENDIENTE|ACEPTADO|RECHAZADO", message = "El estado debe ser PENDIENTE, ACEPTADO o RECHAZADO")
    private String estado;

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
