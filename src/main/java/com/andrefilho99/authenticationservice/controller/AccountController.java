package com.andrefilho99.authenticationservice.controller;

import com.andrefilho99.authenticationservice.domain.User;
import com.andrefilho99.authenticationservice.dto.UserRequest;
import com.andrefilho99.authenticationservice.dto.UserResponse;
import com.andrefilho99.authenticationservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class AccountController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest userRequest) {
        User user = userService.create(
                modelMapper.map(userRequest, User.class)
        );
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @GetMapping("/my-info")
    public ResponseEntity<UserResponse> myInfo(Authentication auth) {
        String email = auth.getName();
        User user = userService.findByEmail(email);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);

        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }
}
