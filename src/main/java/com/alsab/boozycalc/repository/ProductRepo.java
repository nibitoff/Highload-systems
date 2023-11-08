package com.alsab.boozycalc.repository;

import com.alsab.boozycalc.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity, Long> {
    @Query(value = "SELECT * FROM products WHERE NAME = ?1", nativeQuery = true)
    Optional<ProductEntity> findByName(String name);
}
