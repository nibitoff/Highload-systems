package com.alsab.boozycalc.cocktail.service;

import com.alsab.boozycalc.cocktail.service.data.IngredientTypeDataService;
import com.alsab.boozycalc.cocktail.dto.IngredientDto;
import com.alsab.boozycalc.cocktail.service.data.IngredientDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientDataService ingredientDataService;
    private final IngredientTypeDataService ingredientTypeDataService;

    public Mono<IngredientDto> add(IngredientDto dto){
        ingredientTypeDataService.findById(dto.getType().getId());
        return ingredientDataService.add(dto);
    }

    public Mono<IngredientDto> edit(IngredientDto dto){
        ingredientTypeDataService.findById(dto.getType().getId());
        return ingredientDataService.edit(dto);
    }
}
