package com.springrest.rest.service;

import com.springrest.rest.entity.UserEntity;
import java.util.Optional;

public interface UserService {
    Optional<UserEntity> readById(long id);

    UserEntity findByEmail(String email);

    UserEntity save(UserEntity user);
}