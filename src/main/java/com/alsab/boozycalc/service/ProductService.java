package com.alsab.boozycalc.service;

import com.alsab.boozycalc.dto.ProductDto;
import com.alsab.boozycalc.service.data.IngredientDataService;
import com.alsab.boozycalc.service.data.ProductDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDataService productDataService;
    private final IngredientDataService ingredientDataService;

    public ProductDto add(ProductDto productDto){
        ingredientDataService.findById(productDto.getIngredient().getId());
        return productDataService.add(productDto);
    }

    public ProductDto edit(ProductDto productDto){
        ingredientDataService.findById(productDto.getIngredient().getId());
        return productDataService.edit(productDto);
    };
}
