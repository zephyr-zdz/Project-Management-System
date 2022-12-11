package com.example.projectmanagementsystem.service.user;


import com.example.projectmanagementsystem.manager.user.UserManager;
import com.example.projectmanagementsystem.model.ClassAdapter;
import com.example.projectmanagementsystem.model.entity.User;
import com.example.projectmanagementsystem.model.vo.SafeUser;
import com.example.projectmanagementsystem.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("UserUserService")
public class UserService {
    private final UserManager userManager;
    private final ClassAdapter classAdapter;
    @Autowired
    UserService(UserManager userManager, ClassAdapter classAdapter){
        this.userManager = userManager;
        this.classAdapter = classAdapter;
    }

    public Response<SafeUser> login(String name, String password) {
        User user;
        String emailRule = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        if (name.matches(emailRule)) {
            user = userManager.findUserByEmailAndPassword(name, password);
        } else {
            user = userManager.findUserByUsernameAndPassword(name, password);
        }
        if (user == null) {
            return new Response<>(Response.FAIL, "身份验证失败", null);
        }
        else {
            return new Response<>(Response.SUCCESS, "登录成功", classAdapter.fromUser2SafeUser(user));
        }
    }

    public Response<SafeUser> create(User user) {
        if (user.getId() != null) {
            return new Response<>(Response.FAIL, "用户id由系统自动生成", null);
        }
        User sameEmailUser = userManager.findUserByEmail(user.getEmail());
        User sameUsernameUser = userManager.findUserByUsername(user.getUsername());
        if (sameEmailUser != null) {
            return new Response<>(Response.FAIL, "该邮箱已被占用！", null);
        }
        if (sameUsernameUser != null) {
            return new Response<>(Response.FAIL, "该用户名已被占用！", null);
        }
        userManager.create(user);
        return new Response<>(Response.SUCCESS, "用户信息注册成功", classAdapter.fromUser2SafeUser(user));
    }

    public Response<List<SafeUser>> search(String name) {
        List<SafeUser> users = new ArrayList<>();
        for (User user : userManager.findUsersByEmailLike(name)) {
            users.add(classAdapter.fromUser2SafeUser(user));
        }
        for (User user : userManager.findUsersByUsernameLike(name)) {
            if (!users.contains(classAdapter.fromUser2SafeUser(user))) {
                users.add(classAdapter.fromUser2SafeUser(user));
            }
        }
        return new Response<>(Response.SUCCESS, "用户查找完毕", users);
    }

    public Response<String> saveSendKey(String token, Integer userId) {
        User user = userManager.findUserById(userId);
        if (user == null) {
            return new Response<>(Response.FAIL, "用户不存在", null);
        }
        user.setSendKey(token);
        userManager.update(user);
        return new Response<>(Response.SUCCESS, "发送密钥保存成功", token);
    }
}
