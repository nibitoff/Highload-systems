package com.alsab.boozycalc.party.repository;

import com.alsab.boozycalc.party.entity.PurchaseEntity;
import com.alsab.boozycalc.party.entity.PurchaseId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepo extends JpaRepository<PurchaseEntity, PurchaseId> {
    @Query(value = "SELECT * FROM purchases WHERE product_id = ?1", nativeQuery = true)
    List<PurchaseEntity> findByProduct(Long id);
    @Query(value = "SELECT * FROM purchases WHERE party_id = ?1", nativeQuery = true)
    List<PurchaseEntity> findByParty(Long id);

    @Query(value = "SELECT * FROM purchases", nativeQuery = true)
    List<PurchaseEntity> findAllWithPagination(Pageable pageable);
}
