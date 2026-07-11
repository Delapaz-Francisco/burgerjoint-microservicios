package com.burgerjoint.customerservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre {min} y {max} caracteres")
    private String nombre;

    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "Debe ingresar un correo válido")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "El teléfono no puede estar vacío")
    private String telefono;

}
