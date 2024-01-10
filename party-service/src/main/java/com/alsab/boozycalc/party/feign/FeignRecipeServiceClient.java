package com.alsab.boozycalc.party.feign;

import com.alsab.boozycalc.party.dto.RecipeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "recipes", url="http://cocktailsapp:8181/")
public interface FeignRecipeServiceClient {
    @GetMapping("api/v1/recipes/exists")
    ResponseEntity<Boolean> existsById(@RequestParam("id") Long id);

    @GetMapping("api/v1/recipes/find-by-cocktail")
    List<RecipeDto> findAllByCocktail(@RequestParam("id") Long id);
}
