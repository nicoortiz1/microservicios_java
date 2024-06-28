package com.example.orders.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "orders") // el nombre que queremos que se cree en la base de datos
@Getter // genera los métodos getter
@Setter // genera los métodos setter
@AllArgsConstructor // genera los constructores con argumentos, inicializando los campos
@NoArgsConstructor // genera los constructores sin argumentos
@Builder // patrón de diseño builder

public class OrdersModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // se genera automáticamente el id
    private Long id;

    @NotNull(message = "Este campo no puede estar vacío")
    private Long productIds; // Lista de IDs de productos que forman parte de la orden

    @NotBlank(message = "Este campo no puede estar vacío")
    private String customerName;

    @NotNull(message = "Este campo no puede ser nulo")
    @DecimalMin(value = "0.0", message = "Debe ser mayor o igual a cero")
    private Double totalAmount;

    private Boolean status;
}

