package com.alsab.boozycalc.service;

import com.alsab.boozycalc.dto.PurchaseDto;
import com.alsab.boozycalc.exception.ItemNotFoundException;
import com.alsab.boozycalc.service.data.PartyDataService;
import com.alsab.boozycalc.service.data.ProductDataService;
import com.alsab.boozycalc.service.data.PurchaseDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseDataService purchaseDataService;
    private final PartyDataService partyDataService;
    private final ProductDataService productDataService;

    public Iterable<PurchaseDto> findAll(){
        return purchaseDataService.findAll();
    }

    public PurchaseDto add(PurchaseDto purchase) throws ItemNotFoundException{
        return purchaseDataService.add(purchase);
    }

    public PurchaseDto edit(PurchaseDto purchase) throws ItemNotFoundException{
        partyDataService.findById(purchase.getParty().getId());
        productDataService.findById(purchase.getProduct().getId());
        return purchaseDataService.add(purchase);
    }

    public void delete(PurchaseDto purchase) throws ItemNotFoundException{
        purchaseDataService.delete(purchase);
    }
}
