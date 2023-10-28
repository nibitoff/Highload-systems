package com.alsab.boozycalc.mapper;

import com.alsab.boozycalc.dto.IngredientDto;
import com.alsab.boozycalc.entity.IngredientEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    IngredientDto ingredientToDto(IngredientEntity ingredient);
    IngredientEntity dtoToIngredient(IngredientDto dto);
}
