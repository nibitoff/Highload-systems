package com.alsab.boozycalc.party.service;

import com.alsab.boozycalc.party.dto.CocktailDto;
import com.alsab.boozycalc.party.dto.CocktailTypeDto;
import com.alsab.boozycalc.party.feign.FeignCocktailServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CocktailFeignService {
    @Autowired
    FeignCocktailServiceClient feignCocktailServiceClient;

    public Boolean existsById(Long id) {
        Boolean r = feignCocktailServiceClient.cocktailExistsById(id).getBody();
        return r != null && r;
    }
    public CocktailDto findById(Long id) {
        return feignCocktailServiceClient.cocktailFindById(id).block();
    }

    public Boolean typeExistsById(Long id) {
        Boolean r = feignCocktailServiceClient.cocktailTypeExistsById(id);
        return r != null && r;
    }
    public CocktailTypeDto typeFindById(Long id) {
        return feignCocktailServiceClient.cocktailTypeFindById(id);
    }
}
