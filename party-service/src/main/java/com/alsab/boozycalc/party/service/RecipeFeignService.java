package com.alsab.boozycalc.party.service;

import com.alsab.boozycalc.party.dto.RecipeDto;
import com.alsab.boozycalc.party.feign.FeignCocktailServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeFeignService {
    private final FeignCocktailServiceClient cocktailServiceClient;

    public List<RecipeDto> findAllByCocktail(Long id){
        return cocktailServiceClient.recipeFindAllByCocktail(id).toStream().toList();
    }
}
