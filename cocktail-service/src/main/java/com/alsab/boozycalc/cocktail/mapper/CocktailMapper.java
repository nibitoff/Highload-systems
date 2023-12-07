package com.alsab.boozycalc.cocktail.mapper;

import com.alsab.boozycalc.cocktail.dto.CocktailDto;
import com.alsab.boozycalc.cocktail.entity.CocktailEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CocktailMapper {
    CocktailDto cocktailToDto(CocktailEntity cocktail);
    CocktailEntity dtoToCocktail(CocktailDto cocktailDto);
}
