package com.bolsa.trabajos.dto;

public class DatoDTO {

    private Long idPostulacion;
    private String nombre;
    private String correo;
    private String habilidades;
    private String disponibilidad;
    private String cvUrl;
    private String estado;

    public Long getIdPostulacion() { return idPostulacion; }
    public void setIdPostulacion(Long idPostulacion) { this.idPostulacion = idPostulacion; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getHabilidades() { return habilidades; }
    public void setHabilidades(String habilidades) { this.habilidades = habilidades; }
    public String getDisponibilidad() { return disponibilidad; }
    public void setDisponibilidad(String disponibilidad) { this.disponibilidad = disponibilidad; }
    public String getCvUrl() { return cvUrl; }
    public void setCvUrl(String cvUrl) { this.cvUrl = cvUrl; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
