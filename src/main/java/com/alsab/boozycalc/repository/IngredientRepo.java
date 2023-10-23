package com.alsab.boozycalc.repository;

import com.alsab.boozycalc.entity.IngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepo extends JpaRepository<IngredientEntity, Long> {

}
