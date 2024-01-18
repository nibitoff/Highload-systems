package com.alsab.boozycalc.cocktail.service;

import com.alsab.boozycalc.cocktail.dto.ProductDto;
import com.alsab.boozycalc.cocktail.service.data.IngredientDataService;
import com.alsab.boozycalc.cocktail.service.data.ProductDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDataService productDataService;
    private final IngredientDataService ingredientDataService;

    public Mono<ProductDto> add(ProductDto productDto){
        ingredientDataService.findById(productDto.getIngredient().getId());
        return productDataService.add(productDto);
    }

    public Mono<ProductDto> edit(ProductDto productDto){
        ingredientDataService.findById(productDto.getIngredient().getId());
        return productDataService.edit(productDto);
    };
}
