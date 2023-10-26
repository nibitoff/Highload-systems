package com.alsab.boozycalc.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private Long price;
    @ManyToOne
    @JoinColumn(name = "party_id", referencedColumnName = "id")
    private PartyEntity party_id;
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private UserEntity person_id;

    public OrderEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public PartyEntity getParty_id() {
        return party_id;
    }

    public void setParty_id(PartyEntity party_id) {
        this.party_id = party_id;
    }

    public UserEntity getPerson_id() {
        return person_id;
    }

    public void setPerson_id(UserEntity person_id) {
        this.person_id = person_id;
    }
}
