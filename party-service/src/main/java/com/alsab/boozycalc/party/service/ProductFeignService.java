package com.alsab.boozycalc.party.service;

import com.alsab.boozycalc.party.dto.ProductDto;
import com.alsab.boozycalc.party.exception.FeignClientException;
import com.alsab.boozycalc.party.feign.FeignProductServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

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
//        if(r.getStatusCode().is2xxSuccessful()){
//            System.out.println(r);
//            return (ProductDto) r.getBody();
//        }
//        throw new FeignClientException(r.getStatusCode().toString(), "products", (String) r.getBody());
        return r;
    }
}
