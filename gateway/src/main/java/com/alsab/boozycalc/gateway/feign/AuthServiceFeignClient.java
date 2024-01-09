package com.alsab.boozycalc.gateway.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth", url="http://authapp:8182/")
public interface AuthServiceFeignClient {
    @PostMapping("/api/v1/auth/do-filter")
    Boolean doFilter(@RequestBody String token);
}

