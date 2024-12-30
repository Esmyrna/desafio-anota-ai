package com.esmyrna.desafio_anota_ai.repositories;

import com.esmyrna.desafio_anota_ai.domain.products.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
}
