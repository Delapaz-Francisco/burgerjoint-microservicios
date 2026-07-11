package com.burgerjoint.deliveryservice.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "envios")
@Data
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long pedidoId;

    @Column(nullable = false)
    private String direccion;

    private String repartidor;

    // Estado del envío (ej: PENDIENTE, EN_CAMINO, ENTREGADO)
    @Column(nullable = false)
    private String estado;

}
