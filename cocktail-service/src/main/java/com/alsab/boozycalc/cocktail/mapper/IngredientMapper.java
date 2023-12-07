package com.alsab.boozycalc.cocktail.mapper;

import com.alsab.boozycalc.cocktail.dto.IngredientDto;
import com.alsab.boozycalc.cocktail.entity.IngredientEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    IngredientDto ingredientToDto(IngredientEntity ingredient);
    IngredientEntity dtoToIngredient(IngredientDto dto);
}
