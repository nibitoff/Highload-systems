package com.alsab.boozycalc.dto;

public record RecipeDto (
        Long ingredient_id,
        Long cocktail_id,
        float quantity
) {
}
