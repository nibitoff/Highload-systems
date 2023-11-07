package com.alsab.boozycalc.mapper;

import com.alsab.boozycalc.dto.CocktailDto;
import com.alsab.boozycalc.dto.CocktailTypeDto;
import com.alsab.boozycalc.entity.CocktailEntity;
import com.alsab.boozycalc.entity.CocktailTypeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CocktailTypeMapper {
    CocktailTypeDto cocktailTypeToDto(CocktailTypeEntity cocktailType);
    CocktailTypeEntity dtoToCocktailType(CocktailTypeDto cocktailTypeDto);
}
