package com.esmyrna.desafio_anota_ai.services;

import com.esmyrna.desafio_anota_ai.domain.category.Category;
import com.esmyrna.desafio_anota_ai.domain.category.CategoryDTO;
import com.esmyrna.desafio_anota_ai.domain.category.exceptions.CategoryNotFoundException;
import com.esmyrna.desafio_anota_ai.repositories.CategoryRepository;
import com.esmyrna.desafio_anota_ai.services.aws.AwsSnsService;
import com.esmyrna.desafio_anota_ai.services.aws.MessageDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private CategoryRepository repository;
    final private AwsSnsService awsSnsService;
    public CategoryService(CategoryRepository repository, AwsSnsService awsSnsService) {
        this.repository = repository;
        this.awsSnsService = awsSnsService;
    }

    public Category insert(CategoryDTO categodyData) {
        Category category = new Category(categodyData);
        this.repository.save(category);
        this.awsSnsService.publish(new MessageDTO(category.toString()));
        return category;
    }

    public List<Category> getAll(){
       return this.repository.findAll();
    }

    public Category update(String id, CategoryDTO categodyData) {
        Category category = this.repository.findById(id).orElseThrow(CategoryNotFoundException::new);
        if(!categodyData.title().isEmpty()) category.setTitle(categodyData.title());
        if(!categodyData.description().isEmpty()) category.setDescription(categodyData.description());

        this.repository.save(category);
        this.awsSnsService.publish(new MessageDTO(categodyData.toString()));
        return category;
    }

    public void delete(String id) {
        Category category = this.repository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        this.repository.delete(category);

    }

    public Optional<Category> getById(String id){
        return this.repository.findById(id);
    }


}
