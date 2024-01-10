package com.alsab.boozycalc.party.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "menus")
public class MenuEntity {
    @EmbeddedId
    private MenuId id;

    public MenuEntity() {
    }

    public MenuId getId() {
        return id;
    }

    public void setId(MenuId id) {
        this.id = id;
    }
}