package com.alsab.boozycalc.party.service;

import com.alsab.boozycalc.party.dto.ProductDto;
import com.alsab.boozycalc.party.feign.FeignProductServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductFeignService {

    @Autowired
    FeignProductServiceClient feignProductServiceClient;

    public Boolean existsById(Long id) {
        Boolean r = feignProductServiceClient.existsById(id).getBody();
        return r != null && r;
    }
    public ProductDto findById(Long id) {
        ProductDto r = feignProductServiceClient.findById(id);
        return r;
    }
}
