package com.alsab.boozycalc.cocktail.repository;

import com.alsab.boozycalc.cocktail.entity.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity, Long> {
    @Query(value = "SELECT * FROM products WHERE NAME = ?1", nativeQuery = true)
    Optional<ProductEntity> findByName(String name);

    @Query(value = "SELECT * FROM products", nativeQuery = true)
    List<ProductEntity> findAllWithPagination(Pageable pageable);

}
