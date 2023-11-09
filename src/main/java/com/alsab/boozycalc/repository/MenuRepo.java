package com.alsab.boozycalc.repository;
import com.alsab.boozycalc.dto.MenuDto;
import com.alsab.boozycalc.entity.MenuEntity;
import com.alsab.boozycalc.entity.MenuId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuRepo extends JpaRepository<MenuEntity, MenuId> {
    @Query(value = "SELECT * FROM menus WHERE cocktail_id = ?1", nativeQuery = true)
    Optional<MenuEntity> findByCocktail(Long id);
    @Query(value = "SELECT * FROM menus WHERE party_id = ?1", nativeQuery = true)
    Optional<MenuEntity> findByParty(Long id);
}

