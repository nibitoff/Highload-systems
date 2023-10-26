package com.alsab.boozycalc.service;

import com.alsab.boozycalc.dto.IngredientDto;
import com.alsab.boozycalc.entity.IngredientEntity;
import com.alsab.boozycalc.exception.ItemNotFoundException;
import com.alsab.boozycalc.repository.IngredientRepo;
import com.alsab.boozycalc.repository.IngredientTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {
    private final IngredientRepo ingredientRepo;
    private final IngredientTypeRepo ingredientTypeRepo;


    @Autowired
    public IngredientService(IngredientRepo ingredientRepo, IngredientTypeRepo ingredientTypeRepo) {
        this.ingredientRepo = ingredientRepo;
        this.ingredientTypeRepo = ingredientTypeRepo;
    }
    public Iterable<IngredientEntity> findAll() {
        return ingredientRepo.findAll();
    }

    public IngredientEntity addIngredient(IngredientEntity ingredient) throws ItemNotFoundException {
        if(ingredientTypeRepo.findById(ingredient.getType().getId()).isEmpty()) throw new ItemNotFoundException("no ingredient type with id " + ingredient.getType().getId());
        return ingredientRepo.save(ingredient);
    }

    public IngredientEntity editIngredient(IngredientEntity ingredient) throws ItemNotFoundException {
        IngredientEntity ingr = ingredientRepo.findById(ingredient.getId()).orElseThrow(() -> new ItemNotFoundException("ingredient with id " + ingredient.getId() + " was not found"));
        ingr.setName(ingredient.getName());
        ingr.setDescription(ingredient.getDescription());
        ingr.setType(ingredient.getType());
        return ingredientRepo.save(ingr);
    }

    public void deleteIngredient(Long ingredientId) {
        ingredientRepo.deleteById(ingredientId);
    }
}
