package com.example.products.controller;

import com.example.products.model.Product;
import com.example.products.service.ProductService;
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
@RequestMapping("api/products")//se define la ruta
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    //contrl para traer los productos
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProducts (){
        return this.productService.getProducts();
    }

    //ctrl para agregar producto
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> createProducts (@Valid @RequestBody Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            List <String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return productService.createProduct(product);
    }

    //ctrl para actualizar producto
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateProduct(@PathVariable("id") Long id, @RequestBody Product updatedProduct) {
        return productService.updateProduct(id, updatedProduct);
    }

    //ctrl de eliminar producto
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteProduct(@PathVariable("id") Long id) {
        return productService.deleteProduct(id);
    }

    //ctrl para buscar product
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> findByIdProduct(@PathVariable("id") Long id) {
        return productService.findProduct(id);
    }

}
