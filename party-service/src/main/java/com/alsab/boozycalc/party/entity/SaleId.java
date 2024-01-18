package com.alsab.boozycalc.party.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class SaleId {
    @ManyToOne
    @JoinColumn(name = "party_id", referencedColumnName = "id")
    private PartyEntity party;

    @Column(name = "cocktail_type")
    private Long cocktailType;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SaleId saleId = (SaleId) obj;
        return cocktailType.equals(saleId.cocktailType) &&
                party.equals(saleId.party);
    }

    @Override
    public int hashCode() {
        return Objects.hash(party, cocktailType);
    }
}
