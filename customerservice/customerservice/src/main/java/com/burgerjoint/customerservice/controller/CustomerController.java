package com.burgerjoint.customerservice.controller;

import com.burgerjoint.customerservice.dto.CustomerDTO;
import com.burgerjoint.customerservice.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> obtenerTodos() {
        return ResponseEntity.ok(customerService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> obtenerPorId(@PathVariable Long id) {
        CustomerDTO customer = customerService.buscarPorId(id);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> crear(@Valid @RequestBody CustomerDTO customerDTO) {
        CustomerDTO nuevoCustomer = customerService.guardar(customerDTO);
        return new ResponseEntity<>(nuevoCustomer, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> actualizar(@PathVariable Long id, @Valid @RequestBody CustomerDTO customerDTO) {
        CustomerDTO existente = customerService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        customerDTO.setId(id); // Aseguramos que se actualice el ID correcto
        CustomerDTO actualizado = customerService.guardar(customerDTO);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        CustomerDTO existente = customerService.buscarPorId(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        customerService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
