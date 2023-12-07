package com.alsab.boozycalc.cocktail.repository;

import com.alsab.boozycalc.cocktail.entity.CocktailTypeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CocktailTypeRepo extends CrudRepository<CocktailTypeEntity, Long> {
}
