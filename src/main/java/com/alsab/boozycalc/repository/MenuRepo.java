package com.alsab.boozycalc.repository;

import com.alsab.boozycalc.entity.MenuEntity;
import com.alsab.boozycalc.entity.MenuId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepo extends JpaRepository<MenuEntity, MenuId> {
    @Query(value = "SELECT * FROM menus WHERE cocktail_id = ?1", nativeQuery = true)
    List<MenuEntity> findAllByCocktail(Long id);
    @Query(value = "SELECT * FROM menus WHERE party_id = ?1", nativeQuery = true)
    List<MenuEntity> findAllByParty(Long id);
}

