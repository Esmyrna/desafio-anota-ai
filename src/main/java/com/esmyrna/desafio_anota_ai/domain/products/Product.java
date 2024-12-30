package com.esmyrna.desafio_anota_ai.domain.products;

import com.esmyrna.desafio_anota_ai.domain.category.Category;
import com.esmyrna.desafio_anota_ai.domain.category.CategoryDTO;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    private String id;
    private String title;
    private String description;
    private String ownerId;
    private Integer price;
    private String category;

    public Product(ProductDTO productDTO){
        this.description = productDTO.description();
        this.ownerId = productDTO.ownerId();
        this.price = productDTO.price();
        this.title = productDTO.title();
        this.category = productDTO.categoryId();
    }


    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("title", this.title);
        json.put("description", this.description);
        json.put("ownerId", this.ownerId);
        json.put("id", this.id);
        json.put("categoryId", this.category);
        json.put("price", this.price);
        json.put("type", "product");

        return json.toString();
    }

}
