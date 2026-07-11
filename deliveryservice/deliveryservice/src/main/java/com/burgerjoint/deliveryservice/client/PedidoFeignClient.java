package com.burgerjoint.deliveryservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "orderservice", url = "http://localhost:8083/api/pedidos")
public interface PedidoFeignClient {

    @GetMapping("/{id}")
    Object obtenerPedidoPorId(@PathVariable("id") Long id);

}
