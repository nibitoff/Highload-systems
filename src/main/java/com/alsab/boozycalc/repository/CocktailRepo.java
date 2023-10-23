package com.alsab.boozycalc.repository;

import com.alsab.boozycalc.entity.CocktailEntity;
import org.springframework.data.repository.CrudRepository;

public interface CocktailRepo extends CrudRepository<CocktailEntity, Long> {
}
