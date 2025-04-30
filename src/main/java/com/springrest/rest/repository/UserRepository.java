package com.springrest.rest.repository;

import com.springrest.rest.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);

    UserEntity findByUsername(String string);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}