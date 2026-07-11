package com.burgerjoint.deliveryservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class EnvioDTO {

    private Long id;

    @NotNull(message = "El ID del pedido es obligatorio.")
    private Long pedidoId;

    @NotBlank(message = "La dirección de entrega es obligatoria.")
    private String direccion;

    private String repartidor;
    private String estado;

}
