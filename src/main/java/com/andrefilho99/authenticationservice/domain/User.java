package com.andrefilho99.authenticationservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_USER")
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String password;
    private Date created;
    private Date modified;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
