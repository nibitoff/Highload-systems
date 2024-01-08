package com.alsab.boozycalc.party.mapper;

import com.alsab.boozycalc.party.dto.PartyDto;
import com.alsab.boozycalc.party.entity.PartyEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PartyMapper {
    PartyDto partyToDto(PartyEntity party);
    PartyEntity dtoToParty(PartyDto partyDto);
}
