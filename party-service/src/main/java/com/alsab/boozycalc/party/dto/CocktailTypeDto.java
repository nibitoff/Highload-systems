package com.alsab.boozycalc.party.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CocktailTypeDto {
    private Long id;
    private String name;
    private String description;
}
