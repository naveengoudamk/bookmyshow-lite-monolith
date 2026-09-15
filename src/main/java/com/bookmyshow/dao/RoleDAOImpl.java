package com.bookmyshow.dao;

import com.bookmyshow.entity.Role;
import com.bookmyshow.repository.RoleRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RoleDAOImpl implements RoleDAO {

    private final RoleRepository roleRepository;

    public RoleDAOImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }
}