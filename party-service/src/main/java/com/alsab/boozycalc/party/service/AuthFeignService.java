package com.alsab.boozycalc.party.service;

import com.alsab.boozycalc.party.dto.CocktailDto;
import com.alsab.boozycalc.party.dto.UserDto;
import com.alsab.boozycalc.party.feign.FeignAuthServiceClient;
import com.alsab.boozycalc.party.feign.FeignCocktailServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthFeignService {
    @Autowired
    FeignAuthServiceClient feignAuthServiceClient;

    public Boolean existsById(Long id) {
        Boolean r = feignAuthServiceClient.existsById(id).getBody();
        return r != null && r;
    }
    public UserDto findById(Long id) {
        return feignAuthServiceClient.findById(id);
    }

}
