package com.alsab.boozycalc.party.service.data;

import com.alsab.boozycalc.party.dto.PartyDto;
import com.alsab.boozycalc.party.exception.ItemNotFoundException;
import com.alsab.boozycalc.party.mapper.PartyMapper;
import com.alsab.boozycalc.party.repository.PartyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartyDataService {
    private final PartyRepo partyRepo;
    private final PartyMapper mapper;

    public List<PartyDto> findAll() {
        return partyRepo.findAll().stream().map(mapper::partyToDto).toList();
    }

    public PartyDto findById(Long id) {
        return mapper.partyToDto(partyRepo.findById(id).orElseThrow(() -> new ItemNotFoundException(PartyDto.class, id)));
    }

    public void deleteById(Long id) {
        partyRepo.findById(id).orElseThrow(() -> new ItemNotFoundException(PartyDto.class, id));
        partyRepo.deleteById(id);
    }

    public PartyDto add(PartyDto dto) {
        return mapper.partyToDto(partyRepo.save(mapper.dtoToParty(dto)));
    }

    public PartyDto edit(PartyDto dto) {
        partyRepo.findById(dto.getId()).orElseThrow(() -> new ItemNotFoundException(PartyDto.class, dto.getId()));
        return mapper.partyToDto(partyRepo.save(mapper.dtoToParty(dto)));
    }

    public Iterable<PartyDto> findAllWithPagination(Integer page){
        Pageable pageable = PageRequest.of(page, 50);
        return partyRepo.findAllWithPagination(pageable).stream().map(mapper::partyToDto).toList();
    }
}
