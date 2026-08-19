package com.bolsa.trabajos.controller;

import com.bolsa.trabajos.model.Empleador;
import com.bolsa.trabajos.service.EmpleadorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleadores")
public class EmpleadorController {

    @Autowired
    private EmpleadorService empleadorService;

    @PostMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> crearEmpleador(@PathVariable Long idUsuario, @Valid @RequestBody Empleador empleador) {
        Empleador nuevo = empleadorService.crearEmpleador(idUsuario, empleador);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Empleador>> listarEmpleadores() {
        return ResponseEntity.ok(empleadorService.listarEmpleadores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerEmpleador(@PathVariable Long id) {
        return ResponseEntity.ok(empleadorService.obtenerPorId(id));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> obtenerPorUsuario(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(empleadorService.obtenerPorUsuario(idUsuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEmpleador(@PathVariable Long id, @Valid @RequestBody Empleador empleador) {
        return ResponseEntity.ok(empleadorService.actualizarEmpleador(id, empleador));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarEmpleador(@PathVariable Long id) {
        empleadorService.eliminarEmpleador(id);
        return ResponseEntity.noContent().build();
    }
}
