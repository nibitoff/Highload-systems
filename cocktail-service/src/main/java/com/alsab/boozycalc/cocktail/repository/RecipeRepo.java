package com.alsab.boozycalc.cocktail.repository;

import com.alsab.boozycalc.cocktail.entity.RecipeEntity;
import com.alsab.boozycalc.cocktail.entity.RecipeId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepo extends JpaRepository<RecipeEntity, RecipeId> {
    @Query(value = "SELECT * FROM recipes WHERE cocktail_id = ?1", nativeQuery = true)
    List<RecipeEntity> findAllByCocktail(Long id);

    @Query(value = "SELECT * FROM recipes", nativeQuery = true)
    List<RecipeEntity> findAllWithPagination(Pageable pageable);
}
