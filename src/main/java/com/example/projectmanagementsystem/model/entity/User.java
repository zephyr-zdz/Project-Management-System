package com.example.projectmanagementsystem.model.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "user", indexes = {
        @Index(name = "Username_UNIQUE", columnList = "username", unique = true),
        @Index(name = "Email_UNIQUE", columnList = "email", unique = true)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
