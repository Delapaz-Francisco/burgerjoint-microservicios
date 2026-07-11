package com.burgerjoint.orderservice.dto;

import lombok.Data;

@Data
public class MenuDTO {
    private Long id;
    private String nombre;
    private Double precio;
    private Integer stock;

}
