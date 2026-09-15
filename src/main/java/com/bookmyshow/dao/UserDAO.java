package com.bookmyshow.dao;

import com.bookmyshow.entity.User;

import java.util.Optional;

public interface UserDAO {

    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}