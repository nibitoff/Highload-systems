package com.alsab.boozycalc.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PurchaseId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private ProductEntity product_id;

    @ManyToOne
    @JoinColumn(name = "party_id", referencedColumnName = "id")
    private PartyEntity party_id;

    public PurchaseId() {
    }

    public PurchaseId(ProductEntity product_id, PartyEntity party_id) {
        this.product_id = product_id;
        this.party_id = party_id;
    }

    public ProductEntity getProduct_id() {
        return product_id;
    }

    public void setProduct_id(ProductEntity product_id) {
        this.product_id = product_id;
    }

    public PartyEntity getParty_id() {
        return party_id;
    }

    public void setParty_id(PartyEntity party_id) {
        this.party_id = party_id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PurchaseId purchaseId = (PurchaseId) obj;
        return product_id.equals(purchaseId.product_id) &&
                party_id.equals(purchaseId.party_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product_id, party_id);
    }
}