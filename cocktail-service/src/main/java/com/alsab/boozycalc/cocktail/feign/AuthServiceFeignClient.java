package com.alsab.boozycalc.cocktail.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("login-service")
public interface AuthServiceFeignClient {
    @PostMapping("api/v1/auth/validate")
    ResponseEntity<?> validateToken(String token);

    @PostMapping("api/v1/auth/login-from-token")
    ResponseEntity<String> getLoginFromToken(String token);
}
