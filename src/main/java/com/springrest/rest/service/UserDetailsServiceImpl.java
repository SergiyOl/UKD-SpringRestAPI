package com.springrest.rest.service;

import com.springrest.rest.entity.UserDetailsImpl;
import com.springrest.rest.repository.UserRepository;
import com.springrest.rest.entity.UserEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        // .orElseThrow(() -> new UsernameNotFoundException("User Not Found with
        // username: " + username));
        return UserDetailsImpl.build(user);
    }
}