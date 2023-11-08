package com.alsab.boozycalc.repository;

import com.alsab.boozycalc.entity.InviteEntity;
import com.alsab.boozycalc.entity.InviteId;
import com.alsab.boozycalc.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InviteRepo extends JpaRepository<InviteEntity, InviteId> {
    @Query(value = "SELECT * FROM invites WHERE person_id = ?1", nativeQuery = true)
    Optional<InviteEntity> findByUser(Long id);
    @Query(value = "SELECT * FROM invites WHERE party_id = ?1", nativeQuery = true)
    Optional<InviteEntity> findByParty(Long id);
}
