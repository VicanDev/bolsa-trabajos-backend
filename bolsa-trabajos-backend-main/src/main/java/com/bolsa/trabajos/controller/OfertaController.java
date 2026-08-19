package com.bolsa.trabajos.controller;

import com.bolsa.trabajos.dto.ActualizarOfertaRequest;
import com.bolsa.trabajos.dto.OfertaStatsDTO;
import com.bolsa.trabajos.model.Oferta;
import com.bolsa.trabajos.service.OfertaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ofertas")
public class OfertaController {

    @Autowired
    private OfertaService ofertaService;

    private String correoAutenticado() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @PreAuthorize("hasRole('EMPLEADOR')")
    @PostMapping("/empleador/{idEmpleador}/categoria/{idCategoria}")
    public ResponseEntity<?> crearOferta(@PathVariable Long idEmpleador,
                                          @PathVariable Long idCategoria,
                                          @Valid @RequestBody Oferta oferta) {
        Oferta nueva = ofertaService.crearOferta(idEmpleador, idCategoria, oferta, correoAutenticado());
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Oferta>> listarOfertas() {
        return ResponseEntity.ok(ofertaService.listarOfertas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerOferta(@PathVariable Long id) {
        return ResponseEntity.ok(ofertaService.obtenerPorId(id));
    }

    @GetMapping("/empleador/{idEmpleador}")
    public ResponseEntity<List<Oferta>> listarPorEmpleador(@PathVariable Long idEmpleador) {
        return ResponseEntity.ok(ofertaService.listarPorEmpleador(idEmpleador));
    }

    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<List<Oferta>> listarPorCategoria(@PathVariable Long idCategoria) {
        return ResponseEntity.ok(ofertaService.listarPorCategoria(idCategoria));
    }

    @PreAuthorize("hasRole('EMPLEADOR')")
    @GetMapping("/stats")
    public ResponseEntity<List<OfertaStatsDTO>> obtenerEstadisticas() {
        return ResponseEntity.ok(ofertaService.obtenerEstadisticas(correoAutenticado()));
    }

    @PreAuthorize("hasRole('EMPLEADOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarOferta(@PathVariable Long id, @Valid @RequestBody ActualizarOfertaRequest datos) {
        return ResponseEntity.ok(ofertaService.actualizarOferta(id, datos, correoAutenticado()));
    }

    @PreAuthorize("hasRole('EMPLEADOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarOferta(@PathVariable Long id) {
        ofertaService.eliminarOferta(id, correoAutenticado());
        return ResponseEntity.noContent().build();
    }
}
