package com.esmyrna.desafio_anota_ai.controllers;

import com.esmyrna.desafio_anota_ai.domain.category.Category;
import com.esmyrna.desafio_anota_ai.domain.category.CategoryDTO;
import com.esmyrna.desafio_anota_ai.domain.products.Product;
import com.esmyrna.desafio_anota_ai.domain.products.ProductDTO;
import com.esmyrna.desafio_anota_ai.services.CategoryService;
import com.esmyrna.desafio_anota_ai.services.ProductService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/product")
public class ProductController {
    private ProductService service;

    public ProductController(ProductService service){
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<Product> insert(@RequestBody ProductDTO productData){
        Product newProduct = this.service.insert(productData);
        return ResponseEntity.ok().body(newProduct);
    }
    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        List<Product> products = this.service.getAll();
        return ResponseEntity.ok().body(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") String id, @RequestBody ProductDTO productData){
        Product updatedProduct = this.service.update(id, productData);
        return ResponseEntity.ok().body(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable("id") String id){
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
