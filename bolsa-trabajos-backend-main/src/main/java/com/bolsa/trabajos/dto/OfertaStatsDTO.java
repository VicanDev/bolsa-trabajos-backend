package com.bolsa.trabajos.dto;

import java.time.LocalDate;

public class OfertaStatsDTO {

    private Long id;
    private String titulo;
    private String descripcion;
    private String ubicacion;
    private String duracion;
    private String requisitos;
    private String estado;
    private LocalDate fechaPublicacion;
    private Long idCategoria;
    private String categoria;
    private long totalPostulaciones;
    private long pendientes;
    private long aceptados;
    private long rechazados;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public String getDuracion() { return duracion; }
    public void setDuracion(String duracion) { this.duracion = duracion; }
    public String getRequisitos() { return requisitos; }
    public void setRequisitos(String requisitos) { this.requisitos = requisitos; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDate getFechaPublicacion() { return fechaPublicacion; }
    public void setFechaPublicacion(LocalDate fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }
    public Long getIdCategoria() { return idCategoria; }
    public void setIdCategoria(Long idCategoria) { this.idCategoria = idCategoria; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public long getTotalPostulaciones() { return totalPostulaciones; }
    public void setTotalPostulaciones(long totalPostulaciones) { this.totalPostulaciones = totalPostulaciones; }
    public long getPendientes() { return pendientes; }
    public void setPendientes(long pendientes) { this.pendientes = pendientes; }
    public long getAceptados() { return aceptados; }
    public void setAceptados(long aceptados) { this.aceptados = aceptados; }
    public long getRechazados() { return rechazados; }
    public void setRechazados(long rechazados) { this.rechazados = rechazados; }
}
