package com.alsab.boozycalc.notification.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaleNotificationDto {
    @NotNull(message = "Sale amount is mandatory")
    int salePercent;
    @NotNull(message = "Type is mandatory")
    Long cocktailTypeId;
    @NotNull(message = "Party is mandatory")
    Long partyId;
    String description;
}
