package com.alsab.boozycalc.party.feign;

import com.alsab.boozycalc.party.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product")
public interface FeignProductServiceClient {
    @GetMapping("api/v1/products/exists")
    ResponseEntity<Boolean> existsById(@RequestParam("id") Long id);

    @GetMapping("api/v1/products/find")
    ProductDto findById(@RequestParam("id") Long id);
}
