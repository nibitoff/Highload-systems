package com.alsab.boozycalc.repository;

import com.alsab.boozycalc.entity.CocktailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CocktailRepo extends JpaRepository<CocktailEntity, Long> {
    @Query(value = "SELECT * FROM cocktails WHERE NAME = ?1", nativeQuery = true)
    Optional<CocktailEntity> findByName(String name);
}
