package com.alsab.boozycalc.cocktail.mapper;

import com.alsab.boozycalc.cocktail.entity.CocktailTypeEntity;
import com.alsab.boozycalc.cocktail.dto.CocktailTypeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CocktailTypeMapper {
    CocktailTypeDto cocktailTypeToDto(CocktailTypeEntity cocktailType);
    CocktailTypeEntity dtoToCocktailType(CocktailTypeDto cocktailTypeDto);
}
