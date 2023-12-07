package com.alsab.boozycalc.cocktail.mapper;

import com.alsab.boozycalc.cocktail.dto.IngredientTypeDto;
import com.alsab.boozycalc.cocktail.entity.IngredientTypeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IngredientTypeMapper {
    IngredientTypeEntity dtoToIngredientType(IngredientTypeDto dto);
    IngredientTypeDto ingredientTypeToDto(IngredientTypeEntity type);
}
