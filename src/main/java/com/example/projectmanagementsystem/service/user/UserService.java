package com.example.projectmanagementsystem.service.user;


import com.example.projectmanagementsystem.manager.user.UserManager;
import com.example.projectmanagementsystem.model.entity.User;
import com.example.projectmanagementsystem.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service("UserUserService")
public class UserService {
    private final UserManager userManager;

    @Autowired
    UserService(UserManager userManager){
        this.userManager = userManager;
    }

    public Response<String> login(String name, String password){
        User user;
        String emailRule = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        if (name.matches(emailRule)) {
            user = userManager.findUserByEmailAndPassword(name, password);
        } else {
            user = userManager.findUserByUsernameAndPassword(name, password);
        }
        if (user == null) {
            return new Response<>(Response.FAIL, "身份验证失败", "false");
        }
        else {
            return new Response<>(Response.SUCCESS, "登录成功", "true");
        }
    }

    public Response<User> create(User user) {
        if (user.getId() != null) {
            return new Response<>(Response.FAIL, "用户id由系统自动生成", null);
        }
        User sameEmailUser = userManager.getUserByEmail(user.getEmail());
        User sameUsernameUser = userManager.getUserByUsername(user.getUsername());
        if (sameEmailUser != null) {
            return new Response<>(Response.FAIL, "该邮箱已被占用！", null);
        }
        if (sameUsernameUser != null) {
            return new Response<>(Response.FAIL, "该用户名已被占用！", null);
        }
        userManager.create(user);
        return new Response<>(Response.SUCCESS, "用户信息注册成功", user);
    }

}
