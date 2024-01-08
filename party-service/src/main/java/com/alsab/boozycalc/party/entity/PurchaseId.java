package com.alsab.boozycalc.party.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseId implements Serializable {
//    @ManyToOne
//    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @Column(name = "product_id")
    private Long product;

    @ManyToOne
    @JoinColumn(name = "party_id", referencedColumnName = "id")
    private PartyEntity party;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PurchaseId purchaseId = (PurchaseId) obj;
        return product.equals(purchaseId.product) &&
                party.equals(purchaseId.party);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, party);
    }
}