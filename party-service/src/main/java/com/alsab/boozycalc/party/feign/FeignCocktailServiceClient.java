package com.alsab.boozycalc.party.feign;

import com.alsab.boozycalc.party.dto.CocktailDto;
import com.alsab.boozycalc.party.dto.ProductDto;
import com.alsab.boozycalc.party.dto.RecipeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "cocktail-service")
public interface FeignCocktailServiceClient {
    @GetMapping("api/v1/cocktails/exists")
    ResponseEntity<Boolean> cocktailExistsById(@RequestParam("id") Long id);

    @GetMapping("api/v1/cocktails/find")
    CocktailDto cocktailFindById(@RequestParam("id") Long id);

    @GetMapping("api/v1/products/exists")
    ResponseEntity<Boolean> productExistsById(@RequestParam("id") Long id);

    @GetMapping("api/v1/products/find")
    ProductDto productFindById(@RequestParam("id") Long id);

    @GetMapping("api/v1/recipes/exists")
    ResponseEntity<Boolean> recipeExistsById(@RequestParam("id") Long id);

    @GetMapping("api/v1/recipes/find-by-cocktail")
    List<RecipeDto> recipeFindAllByCocktail(@RequestParam("id") Long id);
}
