package com.alsab.boozycalc.cocktail.repository;

import com.alsab.boozycalc.cocktail.entity.IngredientTypeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientTypeRepo extends CrudRepository<IngredientTypeEntity, Long> {
}
