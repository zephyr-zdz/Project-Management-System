package com.example.projectmanagementsystem.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user", indexes = {
        @Index(name = "Username_UNIQUE", columnList = "username", unique = true),
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "username", nullable = false, length = 30)
    private String username;
    @Column(name = "password", length = 30)
    private String password;
    @Column(name = "email", length = 30)
    private String email;

    public void update(User newUser) {
        setUsername(newUser.getUsername());
        setPassword(newUser.getPassword());
        setEmail(newUser.getEmail());
    }
}
