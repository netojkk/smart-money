package com.neto.smart_money.services;

import com.neto.smart_money.domain.entities.category.Category;
import com.neto.smart_money.domain.entities.client.Client;
import com.neto.smart_money.domain.enums.CategoryType;
import com.neto.smart_money.dto.CategoryRequestDTO;
import com.neto.smart_money.dto.CategoryResponseDTO;
import com.neto.smart_money.dto.UpdateCategoryDTO;
import com.neto.smart_money.exceptions.custom.CategoryDuplicateException;
import com.neto.smart_money.exceptions.custom.CategoryNotFoundException;
import com.neto.smart_money.exceptions.custom.InvalidCategoryTypeException;
import com.neto.smart_money.exceptions.custom.UserNotFoundException;
import com.neto.smart_money.repositories.CategoryRepository;
import com.neto.smart_money.repositories.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public Client getAuthenticated(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return clientRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
    }

    public CategoryResponseDTO createCategory(CategoryRequestDTO data){
        Client client = getAuthenticated();

        if (this.categoryRepository.findByNameAndClientId(data.name(), client.getId()).isPresent()){
            throw new CategoryDuplicateException("This category already exists");
        }

        Category category = new Category();
        category.setName(data.name());

        //received in string and transform in ENUM
        CategoryType typeEnum;
        try {
            typeEnum = CategoryType.valueOf(data.type().toUpperCase());
        } catch (IllegalArgumentException e){
            throw new InvalidCategoryTypeException("Invalid category type: " + data.type());
        }
        category.setType(typeEnum);

        category.setClient(client);

        Category created = categoryRepository.save(category);
        return new CategoryResponseDTO(created.getId(), created.getName(), created.getType());
    }

    public List<CategoryResponseDTO> getAllByClient(){
        Client client = getAuthenticated();
        return categoryRepository.findByClientId(client.getId()).stream()
                .map( category -> new CategoryResponseDTO(category.getId(), category.getName(), category.getType()))
                .toList();
    }

    public CategoryResponseDTO editCategoryById(UUID id, UpdateCategoryDTO data){
        Client client = getAuthenticated();

        Category category = this.categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category Not Found"));

        if (data.name() != null && !data.name().equalsIgnoreCase(category.getName())){
            Optional<Category> existingCategory = this.categoryRepository.findByNameAndClientId(data.name(), category.getClient().getId());

            if (existingCategory.isPresent()){
                throw  new CategoryDuplicateException("This category name already exists");
            }
        }

        if (data.name() != null) {
            category.setName(data.name());
        }

        if (data.type() != null) {
            category.setType(data.type());
        }

        Category updated = categoryRepository.save(category);
        return new CategoryResponseDTO(updated.getId(), updated.getName(), updated.getType());
    }

    @Transactional
    public void deleteById(UUID id){
        Client client = getAuthenticated();

        Category category = this.categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category Not Found"));

        categoryRepository.delete(category);
    }
}
