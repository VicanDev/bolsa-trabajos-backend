package com.bolsa.trabajos.controller;

import com.bolsa.trabajos.model.Oferta;
import com.bolsa.trabajos.service.OfertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ofertas")
public class OfertaController {

    @Autowired
    private OfertaService ofertaService;

    @PostMapping("/empleador/{idEmpleador}/categoria/{idCategoria}")
    public ResponseEntity<?> crearOferta(@PathVariable Long idEmpleador,
                                          @PathVariable Long idCategoria,
                                          @RequestBody Oferta oferta) {
        try {
            Oferta nueva = ofertaService.crearOferta(idEmpleador, idCategoria, oferta);
            return new ResponseEntity<>(nueva, HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Oferta>> listarOfertas() {
        return ResponseEntity.ok(ofertaService.listarOfertas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerOferta(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(ofertaService.obtenerPorId(id));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        }
    }

    @GetMapping("/empleador/{idEmpleador}")
    public ResponseEntity<List<Oferta>> listarPorEmpleador(@PathVariable Long idEmpleador) {
        return ResponseEntity.ok(ofertaService.listarPorEmpleador(idEmpleador));
    }

    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<List<Oferta>> listarPorCategoria(@PathVariable Long idCategoria) {
        return ResponseEntity.ok(ofertaService.listarPorCategoria(idCategoria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarOferta(@PathVariable Long id, @RequestBody Oferta oferta) {
        try {
            return ResponseEntity.ok(ofertaService.actualizarOferta(id, oferta));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarOferta(@PathVariable Long id) {
        try {
            ofertaService.eliminarOferta(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        }
    }
}