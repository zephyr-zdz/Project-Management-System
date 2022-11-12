package com.example.projectmanagementsystem.manager.user;
import com.example.projectmanagementsystem.mapper.UserMapper;
import com.example.projectmanagementsystem.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("UserUserManager")
public class UserManager {
    private final UserMapper userMapper;

    @Autowired
    UserManager(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    public User findUserByUsernameAndPassword(String username, String password) {
        return userMapper.findUserByUsernameAndPassword(username, password);
    }

    public User findUserByEmailAndPassword(String email, String password) {
        return userMapper.findUserByEmailAndPassword(email, password);
    }

    public User findUserByEmail(String email) {
        return userMapper.findUserByEmail(email);
    }

    public User findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }

    public void create(User user) {
        userMapper.save(user);
    }

    public User findUserById(Integer id) {
        return userMapper.findUserById(id);
    }

    public List<User> findUsersByEmailLike(String email) {
        return userMapper.findUsersByEmailLike(email);
    }

    public List<User> findUsersByUsernameLike(String name) {
        return userMapper.findUsersByUsernameLike(name);
    }
}
