package com.andrefilho99.authenticationservice.controller;

import com.andrefilho99.authenticationservice.domain.Role;
import com.andrefilho99.authenticationservice.dto.RoleRequest;
import com.andrefilho99.authenticationservice.dto.RoleResponse;
import com.andrefilho99.authenticationservice.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<RoleResponse>> findAll() {

        List<Role> roles = roleService.findAll();
        List<RoleResponse> roleResponses = roles
                .stream()
                .map(role -> modelMapper.map(role, RoleResponse.class))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(roleResponses);
    }

    @PostMapping
    public ResponseEntity<RoleResponse> create(@RequestBody RoleRequest roleRequest) {

        Role role = roleService.create(modelMapper.map(roleRequest, Role.class));
        RoleResponse roleResponse = modelMapper.map(role, RoleResponse.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(roleResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {

        roleService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id}/is-default")
    public ResponseEntity isDefault(@PathVariable Long id) {
        Role role = roleService.isDefault(id);
        RoleResponse roleResponse = modelMapper.map(role, RoleResponse.class);
        return ResponseEntity.status(HttpStatus.OK).body(roleResponse);
    }
}
