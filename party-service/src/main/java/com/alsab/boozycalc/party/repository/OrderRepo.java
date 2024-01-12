package com.alsab.boozycalc.party.repository;

import com.alsab.boozycalc.party.entity.OrderEntity;
import com.alsab.boozycalc.party.entity.PartyEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<OrderEntity, Long> {
    @Query(value = "SELECT * FROM orders WHERE party_id = ?1 AND person_id = ?2 LIMIT 1", nativeQuery = true)
    Optional<OrderEntity> findByPartyAndUser(Long partyId, Long userId);

    @Query(value = "SELECT * FROM orders", nativeQuery = true)
    List<PartyEntity> findAllWithPagination(Pageable pageable);
}
