package com.alsab.boozycalc.mapper;

import com.alsab.boozycalc.dto.IngredientTypeDto;
import com.alsab.boozycalc.entity.IngredientTypeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IngredientTypeMapper {
    IngredientTypeEntity dtoToIngredientType(IngredientTypeDto dto);
    IngredientTypeDto ingredientTypeToDto(IngredientTypeEntity type);
}
