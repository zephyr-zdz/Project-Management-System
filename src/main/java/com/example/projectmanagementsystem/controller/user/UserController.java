package com.example.projectmanagementsystem.controller.user;

import com.example.projectmanagementsystem.model.entity.User;
import com.example.projectmanagementsystem.model.vo.ProjectVO;
import com.example.projectmanagementsystem.model.vo.SafeUser;
import com.example.projectmanagementsystem.service.project.MemberService;
import com.example.projectmanagementsystem.service.project.ProjectService;
import com.example.projectmanagementsystem.service.user.UserService;
import com.example.projectmanagementsystem.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("UserUserController")
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final MemberService memberService;

    @Autowired
    UserController(UserService userService, MemberService memberService) {
        this.userService = userService;
        this.memberService = memberService;
    }

    @PostMapping("/login")
    public Response<SafeUser> login(@RequestParam("username") String name,
                                    @RequestParam("password") String password) {
        return userService.login(name, password);
    }

    @PostMapping("/register")
    public Response<SafeUser> createUser(@RequestBody User user) {
        return userService.create(user);
    }

    @PostMapping("/search")
    public Response<List<SafeUser>> searchUser(@RequestParam("name") String name) {
        return userService.search("%" + name + "%");
    }

    @GetMapping("/participating")
    public Response<List<ProjectVO>> findMyProjects(@RequestParam("user_id") Integer userId) {
        return memberService.findProjects(userId);
    }
}
