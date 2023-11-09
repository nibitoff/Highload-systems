package com.alsab.boozycalc.repository;

import com.alsab.boozycalc.entity.PartyEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartyRepo extends JpaRepository<PartyEntity, Long> {
    @Query(value = "SELECT * FROM parties", nativeQuery = true)
    List<PartyEntity> findAllWithPagination(Pageable pageable);
}
