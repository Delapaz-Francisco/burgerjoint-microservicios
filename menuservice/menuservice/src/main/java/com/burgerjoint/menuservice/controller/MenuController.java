package com.burgerjoint.menuservice.controller;

import com.burgerjoint.menuservice.dto.MenuDTO;
import com.burgerjoint.menuservice.service.MenuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping
    public ResponseEntity<List<MenuDTO>> obtenerTodo() {
        return ResponseEntity.ok(menuService.listarTodo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuDTO> obtenerPorId(@PathVariable Long id) {
        MenuDTO item = menuService.buscarPorId(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<MenuDTO> crear(@Valid @RequestBody MenuDTO menuDTO) {
        MenuDTO nuevoItem = menuService.guardar(menuDTO);
        return new ResponseEntity<>(nuevoItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuDTO> actualizar(@PathVariable Long id, @Valid @RequestBody MenuDTO menuDTO) {
        MenuDTO existente = menuService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        menuDTO.setId(id);
        MenuDTO actualizado = menuService.guardar(menuDTO);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        MenuDTO existente = menuService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        menuService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
