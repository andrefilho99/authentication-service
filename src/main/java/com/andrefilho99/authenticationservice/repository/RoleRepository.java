package com.andrefilho99.authenticationservice.repository;

import com.andrefilho99.authenticationservice.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByIsDefaultTrue();
}
