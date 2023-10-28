package com.alsab.boozycalc.service;

import com.alsab.boozycalc.dto.IngredientDto;
import com.alsab.boozycalc.service.data.IngredientDataService;
import com.alsab.boozycalc.service.data.IngredientTypeDataService;
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
