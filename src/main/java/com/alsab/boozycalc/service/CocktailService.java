package com.alsab.boozycalc.service;

import com.alsab.boozycalc.dto.CocktailDto;
import com.alsab.boozycalc.service.data.CocktailDataService;
import com.alsab.boozycalc.service.data.CocktailTypeDataService;
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
