package com.alsab.boozycalc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDto {
    private ProductDto product;
    private PartyDto party;
    private float quantity;

}
