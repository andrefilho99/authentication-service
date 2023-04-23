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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@RequiredArgsConstructor
@Configuration
public class AppConfiguration {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @PostConstruct
    public void setup() {

        Role role = roleRepository.save(Role.builder().name("ADMIN").isDefault(false).created(new Date()).build());
        userRepository.save(User.builder().email("admin@admin.com").password("admin").role(role).created(new Date()).modified(new Date()).build());
    }
}
