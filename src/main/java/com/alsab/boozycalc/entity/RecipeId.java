package com.alsab.boozycalc.entity;


import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

@Embeddable
public class RecipeId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "ingredient_id", referencedColumnName = "id")
    private IngredientEntity ingredient;

    @ManyToOne
    @JoinColumn(name = "cocktail_id", referencedColumnName = "id")
    private CocktailEntity cocktail;

    public RecipeId() {

    }

    public RecipeId(IngredientEntity ingredient, CocktailEntity cocktail) {
        this.ingredient = ingredient;
        this.cocktail = cocktail;
    }

    public IngredientEntity getIngredient() {
        return ingredient;
    }

    public void setIngredient(IngredientEntity ingredient) {
        this.ingredient = ingredient;
    }

    public CocktailEntity getCocktail() {
        return cocktail;
    }

    public void setCocktail(CocktailEntity cocktail) {
        this.cocktail = cocktail;
    }
}
