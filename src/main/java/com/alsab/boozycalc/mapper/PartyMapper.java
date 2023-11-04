package com.alsab.boozycalc.mapper;

import com.alsab.boozycalc.dto.PartyDto;
import com.alsab.boozycalc.entity.PartyEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PartyMapper {
    PartyDto partyToDto(PartyEntity party);
    PartyEntity dtoToParty(PartyDto partyDto);
}
