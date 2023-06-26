package com.project.jose.account;
//Ayyub Jose

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, unique = true)
    protected String username;

    @NotBlank
    @Size(max = 120)
    @Column(nullable = false)
    protected String password;

    @NotBlank
    @Size(max = 50)
    @Email
    @Column(nullable = false, unique = true)
    protected String email;
}
