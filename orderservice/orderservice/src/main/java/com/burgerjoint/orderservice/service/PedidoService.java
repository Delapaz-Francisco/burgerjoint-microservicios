package com.burgerjoint.orderservice.service;

import com.burgerjoint.orderservice.client.CustomerFeignClient;
import com.burgerjoint.orderservice.client.MenuFeignClient;
import com.burgerjoint.orderservice.dto.CustomerDTO;
import com.burgerjoint.orderservice.dto.MenuDTO;
import com.burgerjoint.orderservice.dto.OrderDTO;
import com.burgerjoint.orderservice.model.Pedido;
import com.burgerjoint.orderservice.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CustomerFeignClient customerFeignClient;

    @Autowired
    private MenuFeignClient menuFeignClient;

    public List<OrderDTO> listarTodos() {
        return pedidoRepository.findAll().stream()
                .map(this::convertirAEntityADto)
                .collect(Collectors.toList());
    }

    public OrderDTO obtenerPedidoPorId(Long id) {
    return pedidoRepository.findById(id)
            .map(this::convertirAEntityADto)
            .orElse(null);
    }

    public OrderDTO crearPedido(OrderDTO orderDTO) {
        CustomerDTO cliente = customerFeignClient.obtenerClientePorId(orderDTO.getClienteId());
        if (cliente == null) {
            throw new RuntimeException("Error: El cliente con ID " + orderDTO.getClienteId() + " no existe.");
        }

        MenuDTO producto = menuFeignClient.obtenerProductoPorId(orderDTO.getMenuId());
        if (producto == null) {
            throw new RuntimeException("Error: El producto con ID " + orderDTO.getMenuId() + " no existe en el menú.");
        }

        if (producto.getStock() < orderDTO.getCantidad()) {
            throw new RuntimeException("Error: No hay suficiente stock. Disponible: " + producto.getStock());
        }

        Double totalCalculado = producto.getPrecio() * orderDTO.getCantidad();
        orderDTO.setTotal(totalCalculado);

        Pedido pedido = convertirADtoAEntity(orderDTO);
        Pedido guardado = pedidoRepository.save(pedido);

        return convertirAEntityADto(guardado);
    }

    public OrderDTO actualizarPedido(Long id, OrderDTO orderDTO) {
        Pedido pedidoExistente = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: El pedido con ID " + id + " no existe."));

        // Validamos si el nuevo cliente o el nuevo menú existen usando Feign Clients
        CustomerDTO cliente = customerFeignClient.obtenerClientePorId(orderDTO.getClienteId());
        if (cliente == null) {
            throw new RuntimeException("Error: El cliente con ID " + orderDTO.getClienteId() + " no existe.");
        }

        MenuDTO producto = menuFeignClient.obtenerProductoPorId(orderDTO.getMenuId());
        if (producto == null) {
            throw new RuntimeException("Error: El producto con ID " + orderDTO.getMenuId() + " no existe en el menú.");
        }

        // Actualizamos los atributos de la entidad existente
        pedidoExistente.setClienteId(orderDTO.getClienteId());
        pedidoExistente.setMenuId(orderDTO.getMenuId());
        pedidoExistente.setCantidad(orderDTO.getCantidad());
        
        // Recalculamos el total automáticamente basado en el precio actual del menú
        Double totalCalculado = producto.getPrecio() * orderDTO.getCantidad();
        pedidoExistente.setTotal(totalCalculado);

        Pedido guardado = pedidoRepository.save(pedidoExistente);
        return convertirAEntityADto(guardado);
    }

    public void eliminarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: El pedido con ID " + id + " no existe."));
        pedidoRepository.delete(pedido);
    }

    private OrderDTO convertirAEntityADto(Pedido pedido) {
        OrderDTO dto = new OrderDTO();
        dto.setId(pedido.getId());
        dto.setClienteId(pedido.getClienteId());
        dto.setMenuId(pedido.getMenuId());
        dto.setCantidad(pedido.getCantidad());
        dto.setTotal(pedido.getTotal());
        return dto;
    }

    private Pedido convertirADtoAEntity(OrderDTO dto) {
        Pedido entity = new Pedido();
        entity.setId(dto.getId());
        entity.setClienteId(dto.getClienteId());
        entity.setMenuId(dto.getMenuId());
        entity.setCantidad(dto.getCantidad());
        entity.setTotal(dto.getTotal());
        return entity;
    }

}
