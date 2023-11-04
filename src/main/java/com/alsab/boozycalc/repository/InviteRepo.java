package com.alsab.boozycalc.repository;

import com.alsab.boozycalc.entity.InviteEntity;
import com.alsab.boozycalc.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InviteRepo extends JpaRepository<InviteEntity, Long> {
}
