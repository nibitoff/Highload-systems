package com.alsab.boozycalc.repository;

import com.alsab.boozycalc.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<OrderEntity, Long> {
    @Query(value = "SELECT * FROM orders WHERE party_id = ?1 AND person_id = ?2", nativeQuery = true)
    Optional<OrderEntity> findByPartyAndUser(Long partyId, Long userId);
}
