package com.alsab.boozycalc.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_entry")
public class OrderEntryEntity {
    @EmbeddedId
    private OrderEntryId id;
    @Column(name = "quantity")
    private int quantity;

    public OrderEntryEntity() {}
    public OrderEntryEntity(OrderEntryId id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderEntryId getId() {
        return id;
    }

    public void setId(OrderEntryId id) {
        this.id = id;
    }
}
