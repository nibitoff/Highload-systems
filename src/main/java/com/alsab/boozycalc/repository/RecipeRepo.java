package com.alsab.boozycalc.repository;

import com.alsab.boozycalc.entity.RecipeEntity;
import com.alsab.boozycalc.entity.RecipeId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepo extends CrudRepository<RecipeEntity, RecipeId> {
}
