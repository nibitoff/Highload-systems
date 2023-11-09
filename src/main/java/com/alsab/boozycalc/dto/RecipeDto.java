package com.alsab.boozycalc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDto {
    @NotNull(message = "Ingredient is mandatory")
    private IngredientDto ingredient;
    @NotNull(message = "Cocktail is mandatory")
    private CocktailDto cocktail;
    @Positive(message = "Price must be positive")
    private float quantity;
}
