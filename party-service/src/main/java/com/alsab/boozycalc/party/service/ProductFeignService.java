package com.alsab.boozycalc.party.service;

import com.alsab.boozycalc.party.dto.ProductDto;
import com.alsab.boozycalc.party.feign.FeignCocktailServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductFeignService {

    @Autowired
    FeignCocktailServiceClient feignCocktailServiceClient;

    public Boolean existsById(Long id) {
        Boolean r = feignCocktailServiceClient.productExistsById(id).getBody();
        return r != null && r;
    }

    public ProductDto findById(Long id) {
        return feignCocktailServiceClient.productFindById(id);
    }
}
