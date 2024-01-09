package com.alsab.boozycalc.cocktail.feign;

import com.alsab.boozycalc.cocktail.security.payload.ValidationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "auth", url="http://authapp:8182/")
public interface AuthServiceFeignClient {
    @PostMapping("/api/v1/auth/validate")
    ResponseEntity<?> validateJwtToken(ValidationRequest request);

    @PostMapping("api/v1/auth/get-login-from-token")
    ResponseEntity<String> getLoginFromToken(ValidationRequest request);
}

