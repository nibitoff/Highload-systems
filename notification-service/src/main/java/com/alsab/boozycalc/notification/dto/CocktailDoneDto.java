package com.alsab.boozycalc.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class CocktailDoneDto {
    String username;
    CocktailDto cocktail;
    PartyDto party;
    Timestamp time;
}
