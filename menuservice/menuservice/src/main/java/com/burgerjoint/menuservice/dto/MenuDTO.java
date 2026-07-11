package com.burgerjoint.menuservice.dto;

import jakarta.validation.constraints.*;
import lombok.Data;


@Data
public class MenuDTO {

    private Long id;

    @NotBlank(message = "El nombre del producto es obligatorio.")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 and 100 caracteres.")
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria.")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio.")
    @Positive(message = "El precio debe ser mayor a cero.")
    private Double precio;

    @NotBlank(message = "La categoría es obligatoria.")
    private String categoria;

    @NotNull(message = "El stock inicial es obligatorio.")
    @Min(value = 0, message = "El stock no puede ser negativo.")
    private Integer stock;

}
