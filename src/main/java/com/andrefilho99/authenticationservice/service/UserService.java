package com.andrefilho99.authenticationservice.service;

import com.andrefilho99.authenticationservice.domain.User;
import com.andrefilho99.authenticationservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException(String.format("User with id %d not found.", id))
                );
    }

    public User create(User user) {

        user.setCreated(new Date());
        user.setModified(new Date());
        user.setRole(roleService.getDefault());
        return userRepository.save(user);
    }

    public User update(Long id, User updatedUser) {

        User user = findById(id);
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());
        user.setModified(new Date());
        return userRepository.save(user);
    }

    public void delete(Long id) {

        User user = findById(id);
        userRepository.delete(user);
    }
}
