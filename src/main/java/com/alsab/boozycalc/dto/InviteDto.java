package com.alsab.boozycalc.dto;

import com.alsab.boozycalc.entity.PartyEntity;
import com.alsab.boozycalc.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InviteDto {
    private PartyDto party;
    private UserDto person;
}
