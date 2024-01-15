package com.alsab.boozycalc.auth.repository;


import com.alsab.boozycalc.auth.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepo extends ReactiveCrudRepository<UserEntity, Long> {
   Mono<UserEntity> findByUsername(String username);
   Mono<Boolean> existsByUsername(String username);
}