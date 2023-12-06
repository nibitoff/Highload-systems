package com.alsab.boozycalc.repository;

import com.alsab.boozycalc.entity.IngredientTypeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientTypeRepo extends CrudRepository<IngredientTypeEntity, Long> {
}
