package com.alsab.boozycalc.service;

import com.alsab.boozycalc.dto.RecipeDto;
import com.alsab.boozycalc.entity.RecipeEntity;
import com.alsab.boozycalc.entity.RecipeId;
import com.alsab.boozycalc.exception.ItemNotFoundException;
import com.alsab.boozycalc.repository.CocktailRepo;
import com.alsab.boozycalc.repository.IngredientRepo;
import com.alsab.boozycalc.repository.RecipeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepo recipeRepo;

    @Autowired
    private IngredientRepo ingredientRepo;
    @Autowired
    private CocktailRepo cocktailRepo;

    public Iterable<RecipeEntity> findAll() {
        return recipeRepo.findAll();
    }

    public RecipeEntity add(RecipeEntity recipe) {
        return recipeRepo.save(recipe);
    }

    public RecipeEntity add(RecipeDto recipe) throws ItemNotFoundException {
        return add(new RecipeEntity(
                new RecipeId(
                        ingredientRepo.findById(recipe.ingredient_id()).orElseThrow(() -> new ItemNotFoundException("no ingredient with id " + recipe.ingredient_id())),
                        cocktailRepo.findById(recipe.cocktail_id()).orElseThrow(() -> new ItemNotFoundException("no ingredient with id " + recipe.ingredient_id()))
                ), recipe.quantity()
        ));
    }
}
