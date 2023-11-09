package com.alsab.boozycalc.repository;

import com.alsab.boozycalc.dto.OrderEntryDto;
import com.alsab.boozycalc.entity.OrderEntryEntity;
import com.alsab.boozycalc.entity.OrderEntryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderEntryRepo extends JpaRepository<OrderEntryEntity, OrderEntryId> {
    @Query(value = "SELECT * FROM order_entry WHERE cocktail_id = ?1", nativeQuery = true)
    Optional<OrderEntryEntity> findByCocktail(Long id);

    @Query(value = "SELECT * FROM order_entry WHERE order_id = ?1", nativeQuery = true)
    Optional<OrderEntryEntity> findByOrder(Long id);

}
