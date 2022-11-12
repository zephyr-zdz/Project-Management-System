package com.example.projectmanagementsystem.service;

import com.example.projectmanagementsystem.manager.InvitationManager;
import com.example.projectmanagementsystem.manager.project.ProjectManager;
import com.example.projectmanagementsystem.manager.user.UserManager;
import com.example.projectmanagementsystem.model.entity.Invitation;
import com.example.projectmanagementsystem.model.entity.User;
import com.example.projectmanagementsystem.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("InvitationService")
public class InvitationService {
    private final InvitationManager invitationManager;
    private final ProjectManager projectManager;
    private final UserManager userManager;

    @Autowired
    InvitationService(InvitationManager invitationManager, ProjectManager projectManager, UserManager userManager) {
        this.invitationManager = invitationManager;
        this.projectManager = projectManager;
        this.userManager = userManager;
    }

    public Response<List<Invitation>> getInvitationList(Integer receiverId) {
        User receiver = userManager.findUserById(receiverId);
        return new Response<>(Response.SUCCESS, "登录成功", invitationManager.findAllByReceiverId(receiverId));
    }
}
