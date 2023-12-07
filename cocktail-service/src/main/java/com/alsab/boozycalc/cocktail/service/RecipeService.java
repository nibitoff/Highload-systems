package com.alsab.boozycalc.cocktail.service;

import com.alsab.boozycalc.cocktail.dto.RecipeDto;
import com.alsab.boozycalc.cocktail.exception.ItemNotFoundException;
import com.alsab.boozycalc.cocktail.service.data.CocktailDataService;
import com.alsab.boozycalc.cocktail.service.data.IngredientDataService;
import com.alsab.boozycalc.cocktail.service.data.RecipeDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeDataService recipeDataService;
    private final IngredientDataService ingredientDataService;
    private final CocktailDataService cocktailDataService;

    public Iterable<RecipeDto> findAll() {
        return recipeDataService.findAll();
    }
    public RecipeDto add(RecipeDto recipe) throws ItemNotFoundException {
        return recipeDataService.add(recipe);
    }

    public RecipeDto edit(RecipeDto recipe) throws ItemNotFoundException {
        ingredientDataService.findById(recipe.getIngredient().getId());
        cocktailDataService.findById(recipe.getCocktail().getId());
        return recipeDataService.add(recipe);
    }

    public void delete(RecipeDto recipe) throws ItemNotFoundException {
        recipeDataService.delete(recipe);
    }
}
