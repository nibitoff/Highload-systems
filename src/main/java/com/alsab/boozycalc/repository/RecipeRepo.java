package com.alsab.boozycalc.repository;

import com.alsab.boozycalc.entity.RecipeEntity;
import com.alsab.boozycalc.entity.RecipeId;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepo extends CrudRepository<RecipeEntity, RecipeId> {
}
