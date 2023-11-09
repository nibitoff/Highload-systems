package com.alsab.boozycalc.mapper;

import com.alsab.boozycalc.dto.InviteDto;
import com.alsab.boozycalc.dto.PartyDto;
import com.alsab.boozycalc.dto.UserDto;
import com.alsab.boozycalc.entity.InviteEntity;
import com.alsab.boozycalc.entity.InviteId;
import com.alsab.boozycalc.entity.PartyEntity;
import com.alsab.boozycalc.entity.UserEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {PartyMapper.class, UserMapper.class})
public interface InviteMapper {
    @Mapping(target = "id", ignore = true)
    InviteEntity dtoToInvite(InviteDto inviteDto);

    @Mapping(target = "party", ignore = true)
    @Mapping(target = "person", ignore = true)
    InviteDto inviteToDto(InviteEntity invite);

    @AfterMapping
    default void mapId(InviteDto dto, @MappingTarget InviteEntity invite){
        PartyMapper party_mapper = Mappers.getMapper(PartyMapper.class);
        UserMapper user_mapper = Mappers.getMapper(UserMapper.class);

        PartyEntity party = party_mapper.dtoToParty(dto.getParty());
        UserEntity user = user_mapper.dtoToUser(dto.getPerson());

        invite.setId(new InviteId(party, user));
    }

    @AfterMapping
    default void mapId(InviteEntity invite, @MappingTarget InviteDto dto){
        PartyMapper party_mapper = Mappers.getMapper(PartyMapper.class);
        UserMapper user_mapper = Mappers.getMapper(UserMapper.class);

        PartyDto partyDto = party_mapper.partyToDto(invite.getId().getParty());
        UserDto userDto = user_mapper.userToDto(invite.getId().getPerson());

        dto.setParty(partyDto);
        dto.setPerson(userDto);
    }
}
