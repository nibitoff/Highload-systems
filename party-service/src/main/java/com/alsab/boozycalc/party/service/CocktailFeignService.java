package com.alsab.boozycalc.party.service;

import com.alsab.boozycalc.party.dto.CocktailDto;
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
        Boolean r = feignCocktailServiceClient.existsById(id).getBody();
        return r != null && r;
    }
    public CocktailDto findById(Long id) {
        return feignCocktailServiceClient.findById(id);
    }
}
