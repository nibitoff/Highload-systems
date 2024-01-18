package com.alsab.boozycalc.party.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class SaleNotificationDto {
    @NotNull(message = "Sale amount is mandatory")
    int salePercent;
    @NotNull(message = "Type is mandatory")
    Long cocktailTypeId;
    @NotNull(message = "Party is mandatory")
    Long partyId;
    String description;

    public SaleNotificationDto() {
    }

    public SaleNotificationDto(int salePercent, Long cocktailTypeId, Long partyId, String description) {
        this.salePercent = salePercent;
        this.cocktailTypeId = cocktailTypeId;
        this.partyId = partyId;
        this.description = description;
    }

    @Override
    public String toString() {
        return "SaleNotificationDto{" +
                "salePercent=" + salePercent +
                ", cocktailTypeId=" + cocktailTypeId +
                ", partyId=" + partyId +
                ", description='" + description + '\'' +
                '}';
    }
}
