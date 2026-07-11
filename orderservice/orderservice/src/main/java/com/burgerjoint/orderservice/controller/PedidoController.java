package com.burgerjoint.orderservice.controller;

import com.burgerjoint.orderservice.dto.OrderDTO;
import com.burgerjoint.orderservice.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<OrderDTO>> obtenerTodos() {
        return ResponseEntity.ok(pedidoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            // Cambiamos "obtenerPorId" por "obtenerPedidoPorId"
            OrderDTO pedido = pedidoService.obtenerPedidoPorId(id);
            
            if (pedido == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Error: El pedido con ID " + id + " no existe.");
            }
            
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody OrderDTO orderDTO) {
        try {
            OrderDTO nuevoPedido = pedidoService.crearPedido(orderDTO);
            return new ResponseEntity<>(nuevoPedido, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody OrderDTO orderDTO) {
        try {
            OrderDTO pedidoActualizado = pedidoService.actualizarPedido(id, orderDTO);
            return ResponseEntity.ok(pedidoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            pedidoService.eliminarPedido(id);
            return ResponseEntity.noContent().build(); // Devuelve un estado 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
