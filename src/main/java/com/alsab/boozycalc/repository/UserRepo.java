package com.alsab.boozycalc.repository;


import com.alsab.boozycalc.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<UserEntity, Long> {
   Optional<UserEntity> findByUsername(String username);
}
