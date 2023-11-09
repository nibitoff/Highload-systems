package com.alsab.boozycalc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private String name;
    private float price;
    private PartyDto party;
    private UserDto person;
}
