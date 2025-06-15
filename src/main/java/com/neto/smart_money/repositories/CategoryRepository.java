package com.neto.smart_money.repositories;

import com.neto.smart_money.domain.entities.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
