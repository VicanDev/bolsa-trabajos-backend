package com.bolsa.trabajos.service;

import com.bolsa.trabajos.dto.ActualizarOfertaRequest;
import com.bolsa.trabajos.dto.OfertaStatsDTO;
import com.bolsa.trabajos.exception.ResourceNotFoundException;
import com.bolsa.trabajos.model.Categoria;
import com.bolsa.trabajos.model.Empleador;
import com.bolsa.trabajos.model.Oferta;
import com.bolsa.trabajos.model.Postulacion;
import com.bolsa.trabajos.model.Usuario;
import com.bolsa.trabajos.repository.CategoriaRepository;
import com.bolsa.trabajos.repository.EmpleadorRepository;
import com.bolsa.trabajos.repository.OfertaRepository;
import com.bolsa.trabajos.repository.PostulacionRepository;
import com.bolsa.trabajos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfertaService {

    @Autowired
    private OfertaRepository ofertaRepository;

    @Autowired
    private EmpleadorRepository empleadorRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private PostulacionRepository postulacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Oferta crearOferta(Long idEmpleador, Long idCategoria, Oferta oferta, String correoSolicitante) {
        Empleador empleador = empleadorRepository.findById(idEmpleador)
                .orElseThrow(() -> new ResourceNotFoundException("Empleador no encontrado"));

        if (!empleador.getUsuario().getCorreo().equalsIgnoreCase(correoSolicitante)) {
            throw new AccessDeniedException("Solo puedes crear ofertas en tu propio nombre");
        }

        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        oferta.setEmpleador(empleador);
        oferta.setCategoria(categoria);
        return ofertaRepository.save(oferta);
    }

    public List<Oferta> listarOfertas() {
        return ofertaRepository.findAll();
    }

    public Oferta obtenerPorId(Long id) {
        return ofertaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Oferta no encontrada"));
    }

    public List<Oferta> listarPorEmpleador(Long idEmpleador) {
        return ofertaRepository.findByEmpleadorId(idEmpleador);
    }

    public List<Oferta> listarPorCategoria(Long idCategoria) {
        return ofertaRepository.findByCategoriaId(idCategoria);
    }

    public Oferta actualizarOferta(Long id, ActualizarOfertaRequest datosNuevos, String correoSolicitante) {
        Oferta oferta = obtenerPorId(id);

        if (!oferta.getEmpleador().getUsuario().getCorreo().equalsIgnoreCase(correoSolicitante)) {
            throw new AccessDeniedException("Solo puedes modificar tus propias ofertas");
        }

        Categoria categoria = categoriaRepository.findById(datosNuevos.getIdCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));

        oferta.setTitulo(datosNuevos.getTitulo());
        oferta.setDescripcion(datosNuevos.getDescripcion());
        oferta.setUbicacion(datosNuevos.getUbicacion());
        oferta.setDuracion(datosNuevos.getDuracion());
        oferta.setRequisitos(datosNuevos.getRequisitos());
        oferta.setCategoria(categoria);
        return ofertaRepository.save(oferta);
    }

    public void eliminarOferta(Long id, String correoSolicitante) {
        Oferta oferta = obtenerPorId(id);

        if (!oferta.getEmpleador().getUsuario().getCorreo().equalsIgnoreCase(correoSolicitante)) {
            throw new AccessDeniedException("Solo puedes eliminar tus propias ofertas");
        }

        ofertaRepository.delete(oferta);
    }

    public List<OfertaStatsDTO> obtenerEstadisticas(String correoSolicitante) {
        Usuario usuario = usuarioRepository.findByCorreo(correoSolicitante)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Empleador empleador = empleadorRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Este usuario no tiene perfil de empleador"));

        return ofertaRepository.findByEmpleadorId(empleador.getId()).stream()
                .map(this::convertirAStats)
                .collect(Collectors.toList());
    }

    private OfertaStatsDTO convertirAStats(Oferta oferta) {
        List<Postulacion> postulaciones = postulacionRepository.findByOfertaId(oferta.getId());
        long pendientes = postulaciones.stream().filter(p -> "PENDIENTE".equalsIgnoreCase(p.getEstado())).count();
        long aceptados = postulaciones.stream().filter(p -> "ACEPTADO".equalsIgnoreCase(p.getEstado())).count();
        long rechazados = postulaciones.stream().filter(p -> "RECHAZADO".equalsIgnoreCase(p.getEstado())).count();

        OfertaStatsDTO dto = new OfertaStatsDTO();
        dto.setId(oferta.getId());
        dto.setTitulo(oferta.getTitulo());
        dto.setDescripcion(oferta.getDescripcion());
        dto.setUbicacion(oferta.getUbicacion());
        dto.setDuracion(oferta.getDuracion());
        dto.setRequisitos(oferta.getRequisitos());
        dto.setEstado(oferta.getEstado());
        dto.setFechaPublicacion(oferta.getFechaPublicacion());
        dto.setIdCategoria(oferta.getCategoria().getId());
        dto.setCategoria(oferta.getCategoria().getNombre());
        dto.setTotalPostulaciones(postulaciones.size());
        dto.setPendientes(pendientes);
        dto.setAceptados(aceptados);
        dto.setRechazados(rechazados);
        return dto;
    }
}
