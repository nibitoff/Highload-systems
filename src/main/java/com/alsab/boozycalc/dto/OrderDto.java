package com.alsab.boozycalc.dto;

import com.alsab.boozycalc.entity.PartyEntity;
import com.alsab.boozycalc.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private String name;
    private Long price;
    private PartyEntity party_id;
    private UserEntity person_id;

}
