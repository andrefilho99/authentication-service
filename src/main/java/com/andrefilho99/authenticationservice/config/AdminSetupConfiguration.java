package com.andrefilho99.authenticationservice.config;

import com.andrefilho99.authenticationservice.domain.Role;
import com.andrefilho99.authenticationservice.domain.User;
import com.andrefilho99.authenticationservice.repository.RoleRepository;
import com.andrefilho99.authenticationservice.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

@Configuration
@RequiredArgsConstructor
public class AdminSetupConfiguration {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;

    @PostConstruct
    public void setup() {
        Role roleAdmin = roleRepository.save(Role.builder().name("ADMIN").isDefault(false).created(new Date()).build());
        Role roleUser = roleRepository.save(Role.builder().name("USER").isDefault(true).created(new Date()).build());
        userRepository.save(User.builder().email("admin").password(encoder.encode("admin")).role(roleAdmin).created(new Date()).modified(new Date()).build());
    }
}
