package com.esmyrna.desafio_anota_ai.services;

import com.esmyrna.desafio_anota_ai.domain.category.Category;
import com.esmyrna.desafio_anota_ai.domain.category.CategoryDTO;
import com.esmyrna.desafio_anota_ai.domain.category.exceptions.CategoryNotFoundException;
import com.esmyrna.desafio_anota_ai.domain.products.Product;
import com.esmyrna.desafio_anota_ai.domain.products.ProductDTO;
import com.esmyrna.desafio_anota_ai.domain.products.exceptions.ProductNotFoundException;
import com.esmyrna.desafio_anota_ai.repositories.ProductRepository;
import com.esmyrna.desafio_anota_ai.services.aws.AwsSnsService;
import com.esmyrna.desafio_anota_ai.services.aws.MessageDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {


    private CategoryService categoryService;
    private ProductRepository repository;
    final private AwsSnsService awsSnsService;

    public ProductService(CategoryService categoryService, ProductRepository productRepository, AwsSnsService awsSnsService) {
        this.categoryService = categoryService;
        this.repository = productRepository;
        this.awsSnsService = awsSnsService;
    }

    public Product insert(ProductDTO productData) {
        this.categoryService.getById(productData.categoryId()).orElseThrow(CategoryNotFoundException::new);
        Product newProduct = new Product(productData);

        this.repository.save(newProduct);
        this.awsSnsService.publish(new MessageDTO(newProduct.toString()));
        return newProduct;
    }

    public List<Product> getAll(){
        return this.repository.findAll();
    }

    public Product update(String id, ProductDTO productData) {
        Product product = this.repository.findById(id).orElseThrow(ProductNotFoundException::new);

        if(productData.categoryId() != null){
        this.categoryService.getById(productData.categoryId())
                    .orElseThrow(CategoryNotFoundException::new);
            product.setCategory(productData.categoryId());
        }

        if(!productData.title().isEmpty()) product.setTitle(productData.title());
        if(!productData.description().isEmpty()) product.setDescription(productData.description());
        if(!(productData.price() == null)) product.setPrice(productData.price());

        this.repository.save(product);

        this.awsSnsService.publish(new MessageDTO(product.toString()));

        return product;
    }

    public void delete(String id) {
        Product product = this.repository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        this.repository.delete(product);


    }
}
