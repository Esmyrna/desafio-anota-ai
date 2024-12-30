package com.esmyrna.desafio_anota_ai.domain.products;

import com.esmyrna.desafio_anota_ai.domain.category.Category;

public record ProductDTO(String title, String description, String ownerId, Integer price, String categoryId) {
}
