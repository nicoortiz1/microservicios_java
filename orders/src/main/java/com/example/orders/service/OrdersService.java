package com.example.orders.service;

import com.example.orders.model.OrdersModel;
import com.example.orders.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final OrdersRepository ordersRepository;

    // Método para traer las órdenes
    public List<OrdersModel> getOrders() {
        return ordersRepository.findAll();
    }

    // Método para crear una orden
    public ResponseEntity<Object> createOrder(OrdersModel order) {
        Long productIds = order.getProductIds();
        String url = "http://localhost:8080/api/products/" + productIds;
        try {
            ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                ordersRepository.save(order);
                return new ResponseEntity<>("Orden creada", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Producto no encontrado, orden no creada", HttpStatus.NOT_FOUND);
            }
        } catch (HttpClientErrorException.NotFound ex) {
            return new ResponseEntity<>("Producto no encontrado, orden no creada", HttpStatus.NOT_FOUND);
        }
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
