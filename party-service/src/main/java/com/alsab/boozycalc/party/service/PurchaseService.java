package com.alsab.boozycalc.party.service;

import com.alsab.boozycalc.party.dto.PurchaseDto;
import com.alsab.boozycalc.party.exception.FeignClientException;
import com.alsab.boozycalc.party.exception.ItemNotFoundException;
import com.alsab.boozycalc.party.service.data.PartyDataService;
import com.alsab.boozycalc.party.service.data.PurchaseDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseDataService purchaseDataService;
    private final PartyDataService partyDataService;
    private final ProductFeignService productService;

    public Iterable<PurchaseDto> findAll(){
        return purchaseDataService.findAll();
    }

    public PurchaseDto add(PurchaseDto purchase) throws ItemNotFoundException, FeignClientException {
        productService.findById(purchase.getProduct().getId());
        return purchaseDataService.add(purchase);
    }

    public PurchaseDto edit(PurchaseDto purchase) throws ItemNotFoundException, FeignClientException {
        partyDataService.findById(purchase.getParty().getId());
        productService.findById(purchase.getProduct().getId());
        return purchaseDataService.add(purchase);
    }

    public void delete(PurchaseDto purchase) throws ItemNotFoundException{
        purchaseDataService.delete(purchase);
    }
}
