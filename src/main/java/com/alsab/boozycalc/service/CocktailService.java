package com.alsab.boozycalc.service;

import com.alsab.boozycalc.dto.CocktailDto;
import com.alsab.boozycalc.entity.CocktailEntity;
import com.alsab.boozycalc.entity.CocktailTypeEntity;
import com.alsab.boozycalc.exception.ItemNotFoundException;
import com.alsab.boozycalc.repository.CocktailRepo;
import com.alsab.boozycalc.repository.CocktailTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CocktailService {
    @Autowired
    CocktailRepo cocktailRepo;

    @Autowired
    CocktailTypeRepo typeRepo;

    public Iterable<CocktailEntity> findAll(){
        return cocktailRepo.findAll();
    }

    public CocktailEntity add(CocktailEntity cocktail){
        return cocktailRepo.save(cocktail);
    }

    public CocktailEntity add(CocktailDto cocktail) throws ItemNotFoundException {
        return add(new CocktailEntity(
                cocktail.name(),
                cocktail.description(),
                cocktail.recipe_description(),
                typeRepo.findById(cocktail.type_id()).orElseThrow(() -> new ItemNotFoundException(CocktailDto.class, cocktail.id()))
        ));
    }

    public Iterable<CocktailTypeEntity> findAllTypes(){
        return typeRepo.findAll();
    }
    public CocktailTypeEntity addType(CocktailTypeEntity type){
        return typeRepo.save(type);
    }
}
