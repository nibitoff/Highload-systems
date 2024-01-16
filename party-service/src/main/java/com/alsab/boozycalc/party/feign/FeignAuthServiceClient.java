package com.alsab.boozycalc.party.feign;

import com.alsab.boozycalc.party.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth")
public interface FeignAuthServiceClient {
    @GetMapping("api/v1/auth/exists")
    ResponseEntity<Boolean> existsById(@RequestParam("id") Long id);

    @GetMapping("api/v1/auth/find")
    UserDto findById(@RequestParam("id") Long id);
}
