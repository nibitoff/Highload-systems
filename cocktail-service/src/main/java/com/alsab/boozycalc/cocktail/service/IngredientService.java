package com.alsab.boozycalc.cocktail.service;

import com.alsab.boozycalc.cocktail.service.data.IngredientTypeDataService;
import com.alsab.boozycalc.cocktail.dto.IngredientDto;
import com.alsab.boozycalc.cocktail.service.data.IngredientDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientDataService ingredientDataService;
    private final IngredientTypeDataService ingredientTypeDataService;

    public IngredientDto add(IngredientDto dto){
        ingredientTypeDataService.findById(dto.getType().getId());
        return ingredientDataService.add(dto);
    }

    public IngredientDto edit(IngredientDto dto){
        ingredientTypeDataService.findById(dto.getType().getId());
        return ingredientDataService.edit(dto);
    }
}
