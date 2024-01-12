package com.alsab.boozycalc.auth.repository;


import com.alsab.boozycalc.auth.entity.UserEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepo extends R2dbcRepository<UserEntity, Long> {
   Mono<UserEntity> findByUsername(String username);
   Mono<Boolean> existsByUsername(String username);
}