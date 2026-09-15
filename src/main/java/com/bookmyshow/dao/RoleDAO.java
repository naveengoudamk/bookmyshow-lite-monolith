package com.bookmyshow.dao;

import com.bookmyshow.entity.Role;

import java.util.Optional;

public interface RoleDAO {

    Role save(Role role);

    Optional<Role> findById(Long id);

    Optional<Role> findByName(String name);
}