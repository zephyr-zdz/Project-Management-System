package com.example.projectmanagementsystem.manager.user;
import com.example.projectmanagementsystem.mapper.UserMapper;
import com.example.projectmanagementsystem.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
