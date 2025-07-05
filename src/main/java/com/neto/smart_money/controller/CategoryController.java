package com.neto.smart_money.controller;

import com.neto.smart_money.dto.CategoryRequestDTO;
import com.neto.smart_money.dto.CategoryResponseDTO;
import com.neto.smart_money.dto.UpdateCategoryDTO;
import com.neto.smart_money.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {

    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryRequestDTO body){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(body));
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<List<CategoryResponseDTO>> getAllByClient(@PathVariable UUID clientId){
        return ResponseEntity.ok(categoryService.getAllByClient(clientId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> editCategoryById(@PathVariable UUID id, @RequestBody UpdateCategoryDTO body){
        return ResponseEntity.ok(categoryService.editCategoryById(id, body));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id){
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
