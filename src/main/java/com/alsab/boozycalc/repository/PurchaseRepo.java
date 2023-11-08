package com.alsab.boozycalc.repository;

import com.alsab.boozycalc.entity.PurchaseEntity;
import com.alsab.boozycalc.entity.PurchaseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepo extends JpaRepository<PurchaseEntity, PurchaseId> {
}
