package com.andrefilho99.authenticationservice.config;

import com.andrefilho99.authenticationservice.domain.Role;
import com.andrefilho99.authenticationservice.domain.User;
import com.andrefilho99.authenticationservice.repository.RoleRepository;
import com.andrefilho99.authenticationservice.repository.UserRepository;
import com.andrefilho99.authenticationservice.service.RoleService;
import com.andrefilho99.authenticationservice.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

@RequiredArgsConstructor
@Configuration
public class AppConfiguration {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @PostConstruct
    public void setup() {
        Role roleAdmin = roleRepository.save(Role.builder().name("ADMIN").isDefault(false).created(new Date()).build());
        Role roleUser = roleRepository.save(Role.builder().name("USER").isDefault(true).created(new Date()).build());
        userRepository.save(User.builder().email("admin").password(encoder.encode("admin")).role(roleAdmin).created(new Date()).modified(new Date()).build());
    }
}
