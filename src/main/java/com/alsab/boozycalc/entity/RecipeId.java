package com.alsab.boozycalc.entity;


import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeId recipeId = (RecipeId) o;
        return Objects.equals(ingredient.getId(), recipeId.ingredient.getId()) && Objects.equals(cocktail.getId(), recipeId.cocktail.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredient, cocktail);
    }
}
