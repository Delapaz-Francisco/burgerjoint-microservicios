package com.burgerjoint.orderservice.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @Column(name = "menu_id", nullable = false)
    private Long menuId;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private Double total;

}
