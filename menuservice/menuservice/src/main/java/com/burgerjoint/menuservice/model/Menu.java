package com.burgerjoint.menuservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Entity
@Table(name = "menus")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotNull(message = "El precio no puede ser nulo")
    @Positive(message = "El precio debe ser un número positivo")
    private Double precio;

    @NotBlank(message = "La categoría es obligatoria (ej: Hamburguesas, Bebidas, Acompañamientos)")
    private String categoria;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser menor a cero")
    private Integer stock;

}
