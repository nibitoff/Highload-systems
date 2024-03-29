package com.alsab.boozycalc.cocktail.repository;

import com.alsab.boozycalc.cocktail.entity.IngredientEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepo extends JpaRepository<IngredientEntity, Long> {
    @Query(value = "SELECT * FROM ingredients WHERE NAME = ?1", nativeQuery = true)
    Optional<IngredientEntity> findByName(String name);

    @Query(value = "SELECT * FROM ingredients", nativeQuery = true)
    List<IngredientEntity> findAllWithPagination(Pageable pageable);
}
