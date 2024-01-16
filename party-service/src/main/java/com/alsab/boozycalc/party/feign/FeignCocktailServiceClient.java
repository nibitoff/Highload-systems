package com.alsab.boozycalc.party.feign;

import com.alsab.boozycalc.party.dto.CocktailDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cocktail")
public interface FeignCocktailServiceClient {
    @GetMapping("api/v1/cocktails/exists")
    ResponseEntity<Boolean> existsById(@RequestParam("id") Long id);

    @GetMapping("api/v1/cocktails/find")
    CocktailDto findById(@RequestParam("id") Long id);
}
