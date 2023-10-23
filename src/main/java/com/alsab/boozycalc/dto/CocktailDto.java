package com.alsab.boozycalc.dto;

public record CocktailDto (
        Long id,
        String name,
        String description,
        String recipe_description,
        java.lang.Long type_id
){
}
