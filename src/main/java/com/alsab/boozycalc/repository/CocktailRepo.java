package com.alsab.boozycalc.repository;

import com.alsab.boozycalc.entity.CocktailEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CocktailRepo extends CrudRepository<CocktailEntity, Long> {
}
