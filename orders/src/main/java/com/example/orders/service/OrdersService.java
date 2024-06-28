package com.example.orders.service;

import com.example.orders.model.OrdersModel;
import com.example.orders.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;

    // Método para traer las órdenes
    public List<OrdersModel> getOrders() {
        return ordersRepository.findAll();
    }

    // Método para crear una orden
    public ResponseEntity<Object> createOrder(OrdersModel order) {
        ordersRepository.save(order);
        return new ResponseEntity<>("Orden creada", HttpStatus.CREATED);
    }

    // Método para actualizar una orden
    public ResponseEntity<Object> updateOrder(Long id, OrdersModel updatedOrder) {
        Optional<OrdersModel> existingOrderOptional = ordersRepository.findById(id);
        if (existingOrderOptional.isPresent()) {
            OrdersModel existingOrder = existingOrderOptional.get();
            existingOrder.setProductIds(updatedOrder.getProductIds());
            existingOrder.setCustomerName(updatedOrder.getCustomerName());
            existingOrder.setTotalAmount(updatedOrder.getTotalAmount());
            existingOrder.setStatus(updatedOrder.getStatus());

            ordersRepository.save(existingOrder);

            return new ResponseEntity<>("Orden actualizada correctamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Orden no encontrada", HttpStatus.NOT_FOUND);
        }
    }

    // Método para eliminar una orden
    public ResponseEntity<Object> deleteOrder(Long id) {
        Optional<OrdersModel> existingOrderOptional = ordersRepository.findById(id);
        if (existingOrderOptional.isPresent()) {
            ordersRepository.deleteById(id);
            return new ResponseEntity<>("Orden eliminada satisfactoriamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Orden no encontrada", HttpStatus.NOT_FOUND);
        }
    }

    // Método para buscar una orden por ID
    public ResponseEntity<Object> findOrder(Long id) {
        Optional<OrdersModel> existingOrderOptional = ordersRepository.findById(id);
        if (existingOrderOptional.isPresent()) {
            OrdersModel existingOrder = existingOrderOptional.get();
            return new ResponseEntity<>(existingOrder, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Orden no encontrada", HttpStatus.NOT_FOUND);
        }
    }
}
