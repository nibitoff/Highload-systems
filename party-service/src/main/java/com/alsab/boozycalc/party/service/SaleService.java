package com.alsab.boozycalc.party.service;

import com.alsab.boozycalc.party.dto.CocktailTypeDto;
import com.alsab.boozycalc.party.dto.PartyDto;
import com.alsab.boozycalc.party.dto.SaleDto;
import com.alsab.boozycalc.party.exception.ItemNotFoundException;
import com.alsab.boozycalc.party.service.data.PartyDataService;
import com.alsab.boozycalc.party.service.data.SaleDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService {
    private final SaleDataService saleDataService;
    private final PartyDataService partyService;
    private final CocktailFeignService cocktailFeignService;

    public List<SaleDto> findByPartyId(Long party) {
        if (!partyService.existsById(party))
            throw new ItemNotFoundException(CocktailTypeDto.class, party);
        return saleDataService.findAllByParty(party);
    }

    public SaleDto add(SaleDto sale) {
        if (!cocktailFeignService.typeExistsById(sale.getCocktailType().getId()))
            throw new ItemNotFoundException(CocktailTypeDto.class, sale.getCocktailType().getId());
        if (!partyService.existsById(sale.getParty().getId()))
            throw new ItemNotFoundException(CocktailTypeDto.class, sale.getParty().getId());

        if (saleDataService.exists(sale.getParty(), sale.getCocktailType())) return sale;
        return saleDataService.add(sale);
    }

    public void deleteByPartyAndType(PartyDto partyDto, CocktailTypeDto cocktailType) {
        saleDataService.delete(partyDto, cocktailType);
    }
}
