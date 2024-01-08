package com.alsab.boozycalc.party.entity;


import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "purchases")
public class PurchaseEntity implements  Serializable{
    @EmbeddedId
    private PurchaseId id;
    @Column(name = "quantity")
    private float quantity;

    public PurchaseEntity() {
    }

    public PurchaseId getId() {
        return id;
    }

    public void setId(PurchaseId id) {
        this.id = id;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }
}