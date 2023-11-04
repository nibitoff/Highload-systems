package com.alsab.boozycalc.repository;

import com.alsab.boozycalc.entity.OrderEntity;
import com.alsab.boozycalc.entity.PartyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<OrderEntity, Long> {
}
