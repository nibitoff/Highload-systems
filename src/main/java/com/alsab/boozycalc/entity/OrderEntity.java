package com.alsab.boozycalc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private float price;
    @ManyToOne
    @JoinColumn(name = "party_id", referencedColumnName = "id")
    private PartyEntity party;
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private UserEntity person;
}
