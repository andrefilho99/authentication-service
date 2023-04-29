package com.andrefilho99.authenticationservice.controller;

import com.andrefilho99.authenticationservice.domain.User;
import com.andrefilho99.authenticationservice.dto.JwtResponse;
import com.andrefilho99.authenticationservice.dto.UserRequest;
import com.andrefilho99.authenticationservice.dto.UserResponse;
import com.andrefilho99.authenticationservice.security.AuthService;
import com.andrefilho99.authenticationservice.security.JwtUtils;
import com.andrefilho99.authenticationservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
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
    private final AuthService authService;
    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest userRequest) {
        User user = userService.create(
                modelMapper.map(userRequest, User.class)
        );
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PostMapping("/token")
    public ResponseEntity<JwtResponse> token(@RequestBody UserRequest userRequest) {
        JwtResponse response = authService.authenticate(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/my-info")
    public ResponseEntity<UserResponse> myInfo(HttpServletRequest request) {
        String jwt = request.getHeader("Authorization").substring(7);
        String email = jwtUtils.extractUsername(jwt);
        User user = userService.findByEmail(email);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);

        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }
}
