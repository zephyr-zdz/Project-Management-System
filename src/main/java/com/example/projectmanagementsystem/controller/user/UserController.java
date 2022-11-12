package com.example.projectmanagementsystem.controller.user;

import com.example.projectmanagementsystem.model.entity.Invitation;
import com.example.projectmanagementsystem.model.entity.User;
import com.example.projectmanagementsystem.model.vo.SafeUser;
import com.example.projectmanagementsystem.service.InvitationService;
import com.example.projectmanagementsystem.service.user.UserService;
import com.example.projectmanagementsystem.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController("UserUserController")
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final InvitationService invitationService;

    @Autowired
    UserController(UserService userService, HttpServletRequest request, InvitationService invitationService) {
        this.userService = userService;
        this.invitationService = invitationService;
    }

    @PostMapping("/login")
    public Response<String> login(@RequestParam("username") String name,
                                  @RequestParam("password") String password) {
        return userService.login(name, password);
    }

    @PostMapping("/register")
    public Response<SafeUser> createUser(@RequestBody User user) {
        return userService.create(user);
    }

    @GetMapping("/my-invitations")
    public Response<List<Invitation>> getMyInvitations(@RequestParam("user_id") Integer userId) {
        return invitationService.getInvitationList(userId);
    }
}
