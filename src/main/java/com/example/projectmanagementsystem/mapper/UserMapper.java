package com.example.projectmanagementsystem.mapper;
import com.example.projectmanagementsystem.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends JpaRepository<User, Integer> {
    User findUserByUsernameAndPassword(String username, String password);

    User findUserByEmailAndPassword(String email, String password);

    User findUserByUsername(String username);

    User findEmailByUsername(String email);

    User findUserById(Integer id);

    User findUserByEmail(String email);

    List<User> findUsersByEmailLike(String email);

    List<User> findUsersByUsernameLike(String name);
}
