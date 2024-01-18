package com.alsab.boozycalc.party.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaleDto {
    private PartyDto party;
    private CocktailTypeDto cocktailType;
    private float amount;
    private String description;

    public SaleDto(Long partyId, Long typeId, float amount, String description) {
        this.party = PartyDto.builder().id(partyId).build();
        this.cocktailType = CocktailTypeDto.builder().id(typeId).build();
        this.amount = amount;
        this.description = description;
    }

    public SaleDto(SaleNotificationDto notificationDto) {
        this(notificationDto.getPartyId(), notificationDto.getCocktailTypeId(), notificationDto.getSalePercent(), notificationDto.getDescription());
    }
}
