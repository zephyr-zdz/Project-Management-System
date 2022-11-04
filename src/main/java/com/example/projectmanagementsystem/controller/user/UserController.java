package com.example.projectmanagementsystem.controller.user;

import com.example.projectmanagementsystem.model.entity.User;
import com.example.projectmanagementsystem.model.vo.SafeUser;
import com.example.projectmanagementsystem.service.user.UserService;
import com.example.projectmanagementsystem.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController("UserUserController")
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    UserController(UserService userService, HttpServletRequest request) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public Response<String> login(@RequestParam("username") String name,
                                  @RequestParam("password") String password) {
        return userService.login(name, password);
    }

    @PostMapping("/register")
    public Response<SafeUser> create(@RequestBody User user) {
        System.out.println(user);
        return userService.create(user);
    }
}
