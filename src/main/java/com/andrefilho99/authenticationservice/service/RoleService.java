package com.andrefilho99.authenticationservice.service;

import com.andrefilho99.authenticationservice.domain.Role;
import com.andrefilho99.authenticationservice.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role findById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException(String.format("Role with id %d not found.", id))
                );
    }

    public Role getDefault() {
        return roleRepository.findByIsDefaultTrue()
                .orElseThrow(
                        () -> new NoSuchElementException(String.format("There is no default role."))
                );
    }

    public Role create(Role role) {
        role.setCreated(new Date());
        role.setIsDefault(false);
        return roleRepository.save(role);
    }

    public Role update(Long id, Role updatedRole) {

        Role role = findById(id);
        role.setName(updatedRole.getName());
        role.setIsDefault(updatedRole.getIsDefault());
        return roleRepository.save(role);
    }

    public void delete(Long id) {
        Role role = findById(id);
        roleRepository.delete(role);
    }

    @Transactional
    public Role isDefault(Long id) {

        Role defaultRole = findById(id);
        List<Role> roles = findAll();

        roles.remove(defaultRole);
        for (Role role : roles) {
            role.setIsDefault(false);
        }

        defaultRole.setIsDefault(true);
        roleRepository.saveAll(roles);
        return roleRepository.save(defaultRole);
    }
}
