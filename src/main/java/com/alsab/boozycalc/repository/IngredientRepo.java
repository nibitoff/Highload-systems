package com.alsab.boozycalc.repository;

import com.alsab.boozycalc.entity.IngredientEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepo extends CrudRepository<IngredientEntity, Long> {

}
