package com.example.products.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "product") // el nombre que queremos que se cree en la base de datos
@Getter // genera los métodos getter
@Setter // genera los métodos setter
@AllArgsConstructor // genera los constructores con argumentos, inicializando los campos
@NoArgsConstructor // genera los constructores sin argumentos
@Builder // patrón de diseño builder

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // se genera automáticamente el id
    private Long id;

    @NotBlank(message = "Este campo no puede estar vacío")
    private String sku;

    @NotBlank(message = "Este campo no puede estar vacío")
    private String name;

    @NotNull(message = "Este campo no puede ser nulo")
    @DecimalMin(value = "0.0", message = "Debe ser mayor o igual a cero")
    private Double price;

    private Boolean status;
}