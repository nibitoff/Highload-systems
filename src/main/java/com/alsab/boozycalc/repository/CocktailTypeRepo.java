package com.alsab.boozycalc.repository;

import com.alsab.boozycalc.entity.CocktailTypeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CocktailTypeRepo extends CrudRepository<CocktailTypeEntity, Long> {
}
