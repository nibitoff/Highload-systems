package com.alsab.boozycalc.entity;


import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class RecipeId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "ingredient_id", referencedColumnName = "id")
    private IngredientEntity ingredient;

    @ManyToOne
    @JoinColumn(name = "cocktail_id", referencedColumnName = "id")
    private CocktailEntity cocktail;

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
