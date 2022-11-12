package com.example.projectmanagementsystem.controller.user;

import com.example.projectmanagementsystem.model.entity.Invitation;
import com.example.projectmanagementsystem.service.InvitationService;
import com.example.projectmanagementsystem.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("UserInvitationController")
@RequestMapping("/user/invitation")
public class InvitationController {
    private final InvitationService invitationService;

    @Autowired
    InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @GetMapping("")
    public Response<List<Invitation>> getMyInvitations(@RequestParam("user_id") Integer userId) {
        return invitationService.getInvitationList(userId);
    }

    @PostMapping("/accept")
    public Response<String> acceptInvitation(@RequestParam("invitation_id") Integer invitationId) {
        return invitationService.accept(invitationId);
    }

    @PostMapping("/refuse")
    public Response<String> refuseInvitation(@RequestParam("invitation_id") Integer invitationId) {
        return invitationService.refuse(invitationId);
    }
}
