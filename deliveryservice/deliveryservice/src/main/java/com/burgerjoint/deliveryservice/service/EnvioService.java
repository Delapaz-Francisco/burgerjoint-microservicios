package com.burgerjoint.deliveryservice.service;

import com.burgerjoint.deliveryservice.client.PedidoFeignClient;
import com.burgerjoint.deliveryservice.dto.EnvioDTO;
import com.burgerjoint.deliveryservice.model.Envio;
import com.burgerjoint.deliveryservice.repository.EnvioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnvioService {

    @Autowired
    private EnvioRepository envioRepository;

    @Autowired
    private PedidoFeignClient pedidoFeignClient;

    public EnvioDTO crearEnvio(EnvioDTO envioDTO) {
        System.out.println("====== PROCESANDO ENVÍO ======");
        System.out.println("Validando pedido ID: " + envioDTO.getPedidoId() + " vía Feign Client...");

        // 1. VALIDACIÓN ESTRICTA: Si falla o no existe, se detiene el flujo
        try {
            Object pedido = pedidoFeignClient.obtenerPedidoPorId(envioDTO.getPedidoId());
            if (pedido == null) {
                throw new IllegalArgumentException("Error: El pedido con ID " + envioDTO.getPedidoId() + " no existe.");
            }
            System.out.println("¡Pedido validado con éxito! Procediendo a guardar...");
        } catch (Exception e) {
            // Bloquea el proceso si hay problemas de comunicación o el ID no es válido
            throw new IllegalArgumentException("No se puede registrar el envío: El pedido no existe o el servicio de pedidos está caído.");
        }

        // 2. PERSISTENCIA LOCAL: Solo se ejecuta si el try de arriba fue exitoso
        Envio envio = new Envio();
        envio.setPedidoId(envioDTO.getPedidoId());
        envio.setDireccion(envioDTO.getDireccion());
        envio.setRepartidor(envioDTO.getRepartidor() != null ? envioDTO.getRepartidor() : "Por asignar");
        envio.setEstado("PENDIENTE");

        Envio guardado = envioRepository.save(envio);
        return mapearADTO(guardado);
    }

    private EnvioDTO mapearADTO(Envio envio) {
        EnvioDTO dto = new EnvioDTO();
        dto.setId(envio.getId());
        dto.setPedidoId(envio.getPedidoId());
        dto.setDireccion(envio.getDireccion());
        dto.setRepartidor(envio.getRepartidor());
        dto.setEstado(envio.getEstado());
        return dto;
    }
}
