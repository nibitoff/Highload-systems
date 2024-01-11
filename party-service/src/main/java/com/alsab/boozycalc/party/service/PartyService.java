package com.alsab.boozycalc.party.service;

import com.alsab.boozycalc.party.dto.*;
import com.alsab.boozycalc.party.exception.NoCocktailInMenuException;
import com.alsab.boozycalc.party.exception.NoIngredientsForCocktailException;
import com.alsab.boozycalc.party.service.data.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PartyService {

    private final PartyDataService partyDataService;
    private final PurchaseDataService purchaseDataService;

    public PartyDto add(PartyDto dto) {
        return partyDataService.add(dto);
    }

    public PartyDto edit(PartyDto dto) {
        partyDataService.findById(dto.getId());
        return partyDataService.edit(dto);
    }

    public List<PurchaseDto> getPartyPurchases(Long id) {
        PartyDto party = partyDataService.findById(id);
        return purchaseDataService.findAllByParty(party.getId());
    }


}