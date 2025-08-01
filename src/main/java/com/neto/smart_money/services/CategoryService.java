package com.neto.smart_money.services;

import com.neto.smart_money.domain.entities.category.Category;
import com.neto.smart_money.domain.entities.client.Client;
import com.neto.smart_money.domain.enums.CategoryType;
import com.neto.smart_money.dto.CategoryRequestDTO;
import com.neto.smart_money.dto.CategoryResponseDTO;
import com.neto.smart_money.dto.UpdateCategoryDTO;
import com.neto.smart_money.exceptions.CategoryDuplicateException;
import com.neto.smart_money.exceptions.CategoryNotFoundException;
import com.neto.smart_money.exceptions.UserNotFoundException;
import com.neto.smart_money.repositories.CategoryRepository;
import com.neto.smart_money.repositories.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryService {

    private ClientRepository clientRepository;
    private CategoryRepository categoryRepository;

    public CategoryResponseDTO createCategory(CategoryRequestDTO data){
        Client client = this.clientRepository.findById(data.clientId())
                .orElseThrow(() -> new UserNotFoundException("User Not Found!"));

        if (this.categoryRepository.findByNameAndClientId(data.name(), data.clientId()).isPresent()){
            throw new CategoryDuplicateException("This category already exists");
        }

        Category category = new Category();
        category.setName(data.name());

        //received in string and transform in ENUM
        CategoryType typeEnum = CategoryType.valueOf(data.type().toUpperCase());
        category.setType(typeEnum);

        category.setClient(client);

        Category created = categoryRepository.save(category);
        return new CategoryResponseDTO(created.getId(), created.getName(), created.getType());
    }

    public List<CategoryResponseDTO> getAllByClient(UUID clientID){
        return categoryRepository.findByClientId(clientID).stream()
                .map( category -> new CategoryResponseDTO(category.getId(), category.getName(), category.getType()))
                .toList();
    }

    public CategoryResponseDTO editCategoryById(UUID id, UpdateCategoryDTO data){
        Category category = this.categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category Not Found"));

        category.setName(data.name() != null ? data.name() : category.getName());
        category.setType(data.type() != null ? data.type() : category.getType());

        Category updated = categoryRepository.save(category);
        return new CategoryResponseDTO(updated.getId(), updated.getName(), updated.getType());
    }

    @Transactional
    public void deleteById(UUID id){
        Category category = this.categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category Not Found"));

        categoryRepository.deleteById(id);
    }
}
