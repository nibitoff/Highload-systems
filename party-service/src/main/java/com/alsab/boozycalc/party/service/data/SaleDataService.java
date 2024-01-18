package com.alsab.boozycalc.party.service.data;

import com.alsab.boozycalc.party.dto.CocktailTypeDto;
import com.alsab.boozycalc.party.dto.PartyDto;
import com.alsab.boozycalc.party.dto.SaleDto;
import com.alsab.boozycalc.party.entity.SaleId;
import com.alsab.boozycalc.party.mapper.PartyMapper;
import com.alsab.boozycalc.party.mapper.SaleMapper;
import com.alsab.boozycalc.party.repository.SaleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleDataService {
    private final SaleRepo repo;
    private final SaleMapper mapper;
    private final PartyMapper partyMapper;

    public List<SaleDto> findAllByParty(Long id) {
        return repo.findAllByParty(id).stream().map(mapper::saleToDto).toList();
    }

    public SaleDto add(SaleDto sale) {
        return mapper.saleToDto(repo.save(mapper.dtoToSale(sale)));
    }

    public void delete(PartyDto party, CocktailTypeDto type) {
        repo.deleteById(new SaleId(partyMapper.dtoToParty(party), type.getId()));
    }

    public boolean exists(PartyDto party, CocktailTypeDto type) {
        return repo.existsById(new SaleId(partyMapper.dtoToParty(party), type.getId()));
    }
}
