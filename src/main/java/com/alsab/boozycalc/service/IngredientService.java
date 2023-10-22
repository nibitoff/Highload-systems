package com.alsab.boozycalc.service;

import com.alsab.boozycalc.entity.IngredientEntity;
import com.alsab.boozycalc.exception.ItemNotFoundException;
import com.alsab.boozycalc.repository.IngredientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {
    @Autowired
    private IngredientRepo ingredientRepo;

    public Iterable<IngredientEntity> findAll() {
        return ingredientRepo.findAll();
    }

    public IngredientEntity addIngredient(IngredientEntity ingredient) {
        return ingredientRepo.save(ingredient);
    }

    public IngredientEntity editIngredient(IngredientEntity ingredient) throws ItemNotFoundException {
        IngredientEntity ingr = ingredientRepo.findById(ingredient.getId()).orElseThrow(() -> new ItemNotFoundException("ingredient with id " + ingredient.getId() + " was not found"));
        ingr.setName(ingredient.getName());
        ingr.setDescription(ingredient.getDescription());
        ingr.setType(ingredient.getType());
        return ingredientRepo.save(ingredient);
    }

    public void deleteIngredient(Long ingredientId) {
        ingredientRepo.deleteById(ingredientId);
    }
}
