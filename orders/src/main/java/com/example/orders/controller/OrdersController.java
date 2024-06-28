package com.example.orders.controller;

import com.example.orders.model.OrdersModel;
import com.example.orders.service.OrdersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/orders") // se define la ruta
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;

    // Controlador para traer las órdenes
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrdersModel> getOrders() {
        return this.ordersService.getOrders();
    }

    // Controlador para agregar una orden
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createOrder(@Valid @RequestBody OrdersModel order, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return ordersService.createOrder(order);
    }

    // Controlador para actualizar una orden
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOrder(@PathVariable("id") Long id, @RequestBody OrdersModel updatedOrder) {
        return ordersService.updateOrder(id, updatedOrder);
    }

    // Controlador para eliminar una orden
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable("id") Long id) {
        return ordersService.deleteOrder(id);
    }

    // Controlador para buscar una orden por ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> findByIdOrder(@PathVariable("id") Long id) {
        return ordersService.findOrder(id);
    }
}
