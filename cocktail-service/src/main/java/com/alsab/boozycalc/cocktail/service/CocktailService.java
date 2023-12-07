package com.alsab.boozycalc.cocktail.service;

import com.alsab.boozycalc.cocktail.service.data.CocktailDataService;
import com.alsab.boozycalc.cocktail.service.data.CocktailTypeDataService;
import com.alsab.boozycalc.cocktail.dto.CocktailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CocktailService {
    private final CocktailDataService cocktailDataService;
    private final CocktailTypeDataService cocktailTypeDataService;

    public CocktailDto add(CocktailDto dto){
        cocktailTypeDataService.findById(dto.getType().getId());
        return cocktailDataService.add(dto);
    }

    public CocktailDto edit(CocktailDto dto){
        cocktailTypeDataService.findById(dto.getType().getId());
        return cocktailDataService.edit(dto);
    }

}
