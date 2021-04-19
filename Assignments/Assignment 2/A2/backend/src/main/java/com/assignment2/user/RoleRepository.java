package com.assignment2.user;

import com.assignment2.user.model.ERole;
import com.assignment2.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole role);
}
