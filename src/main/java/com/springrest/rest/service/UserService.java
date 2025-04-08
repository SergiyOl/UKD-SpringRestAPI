package com.springrest.rest.service;

import java.util.Optional;

import com.springrest.rest.entity.UserEntity;

public interface UserService {
    Optional<UserEntity> findByUsername(String username);

    UserEntity sevaUser(UserEntity user);
}
