package com.example.projectmanagementsystem.service.user;


import com.example.projectmanagementsystem.manager.user.UserManager;
import com.example.projectmanagementsystem.model.entity.User;
import com.example.projectmanagementsystem.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service("UserUserService")
public class UserService {
    UserManager userManager;

    @Autowired
    UserService(UserManager userManager){
        this.userManager = userManager;
    }

    public Response<String> login(String username, String password){
        User user = userManager.findUserByUsernameAndPassword(username, password);
        if (user == null) {
            return new Response<>(Response.FAIL, "身份验证失败", "false");
        }
        else {
            return new Response<>(Response.SUCCESS, "登录成功", "true");
        }
    }
}
