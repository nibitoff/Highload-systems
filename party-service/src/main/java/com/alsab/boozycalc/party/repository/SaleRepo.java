package com.alsab.boozycalc.party.repository;

import com.alsab.boozycalc.party.entity.SaleEntity;
import com.alsab.boozycalc.party.entity.SaleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepo extends JpaRepository<SaleEntity, SaleId> {
    @Query(value = "SELECT * FROM menus WHERE party_id = ?1", nativeQuery = true)
    List<SaleEntity> findAllByParty(Long id);
}
