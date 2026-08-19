package com.bolsa.trabajos.controller;

import com.bolsa.trabajos.service.DatosPruebaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private DatosPruebaService datosPruebaService;

    @PostMapping("/reiniciar-bd")
    public ResponseEntity<Map<String, Object>> reiniciarBaseDatos() {
        return ResponseEntity.ok(datosPruebaService.reiniciarBaseDatos());
    }
}
