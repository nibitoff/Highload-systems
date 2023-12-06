package com.alsab.boozycalc.mapper;

import com.alsab.boozycalc.dto.CocktailDto;
import com.alsab.boozycalc.entity.CocktailEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CocktailMapper {
    CocktailDto cocktailToDto(CocktailEntity cocktail);
    CocktailEntity dtoToCocktail(CocktailDto cocktailDto);
}
