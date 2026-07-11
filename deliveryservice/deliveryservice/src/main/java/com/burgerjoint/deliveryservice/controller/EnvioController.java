package com.burgerjoint.deliveryservice.controller;

import com.burgerjoint.deliveryservice.dto.EnvioDTO;
import com.burgerjoint.deliveryservice.service.EnvioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/envios")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @PostMapping
    public ResponseEntity<EnvioDTO> crear(@Valid @RequestBody EnvioDTO envioDTO) {
        EnvioDTO nuevoEnvio = envioService.crearEnvio(envioDTO);
        return new ResponseEntity<>(nuevoEnvio, HttpStatus.CREATED);
    }

}
