package com.andrefilho99.authenticationservice.controller;

import com.andrefilho99.authenticationservice.domain.User;
import com.andrefilho99.authenticationservice.dto.UserRequest;
import com.andrefilho99.authenticationservice.dto.UserResponse;
import com.andrefilho99.authenticationservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {

        List<User> users = userService.findAll();
        List<UserResponse> userResponses = users
                .stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(userResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {

        User user = userService.findById(id);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);

        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest userRequest) {

        User user = userService.create(
                modelMapper.map(userRequest, User.class)
        );
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody UserRequest userRequest) {

        User user = userService.update(
                id,
                modelMapper.map(userRequest, User.class)
        );
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);

        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {

        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
