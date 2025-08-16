package com.neto.smart_money.repositories;

import com.neto.smart_money.domain.entities.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findByClientId(UUID clientId);
    Optional<Category> findByNameAndClientId(String name, UUID clientId);
    Optional<Category> findByIdAndClientId(UUID categoryId, UUID clientId);

}
