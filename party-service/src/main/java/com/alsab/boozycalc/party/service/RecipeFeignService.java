package com.alsab.boozycalc.party.service;

import com.alsab.boozycalc.party.dto.RecipeDto;
import com.alsab.boozycalc.party.feign.FeignRecipeServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeFeignService {
    private final FeignRecipeServiceClient recipeServiceClient;

    public List<RecipeDto> findAllByCocktail(Long id){
        return recipeServiceClient.findAllByCocktail(id);
    }
}
