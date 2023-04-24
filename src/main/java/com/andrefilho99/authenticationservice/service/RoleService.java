package com.andrefilho99.authenticationservice.service;

import com.andrefilho99.authenticationservice.domain.Role;
import com.andrefilho99.authenticationservice.exceptions.NoDefaultRoleException;
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

    public Role findByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(
                        () -> new NoSuchElementException(String.format("Role with name %s not found.", name))
                );
    }

    public Role getDefault() {
        return roleRepository.findByIsDefaultTrue()
                .orElseThrow(
                        () -> new NoDefaultRoleException(String.format("There is no default role to assign to new User."))
                );
    }

    public Role create(Role role) {
        role.setCreated(new Date());
        role.setIsDefault(false);
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
