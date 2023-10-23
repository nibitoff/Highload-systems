package com.alsab.boozycalc.entity;
import jakarta.persistence.*;

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