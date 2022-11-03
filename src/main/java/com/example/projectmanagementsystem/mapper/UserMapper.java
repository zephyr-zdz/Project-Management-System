package com.example.projectmanagementsystem.mapper;
import com.example.projectmanagementsystem.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends JpaRepository<User, Integer> {
    User findUserByUsernameAndPassword(String username, String password);
    User findUserByUsername(String jobnum);
    User findUserById(Integer id);
}
