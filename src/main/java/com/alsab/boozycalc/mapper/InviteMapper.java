package com.alsab.boozycalc.mapper;

import com.alsab.boozycalc.dto.InviteDto;
import com.alsab.boozycalc.entity.InviteEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InviteMapper {
    InviteDto inviteToDto(InviteEntity invite);
    InviteEntity dtoToInvite(InviteDto inviteDto);
}
