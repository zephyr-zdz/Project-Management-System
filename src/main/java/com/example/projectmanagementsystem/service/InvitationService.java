package com.example.projectmanagementsystem.service;

import com.example.projectmanagementsystem.manager.InvitationManager;
import com.example.projectmanagementsystem.manager.project.MemberManager;
import com.example.projectmanagementsystem.manager.project.ProjectManager;
import com.example.projectmanagementsystem.manager.user.UserManager;
import com.example.projectmanagementsystem.model.entity.Invitation;
import com.example.projectmanagementsystem.model.entity.Project;
import com.example.projectmanagementsystem.model.entity.User;
import com.example.projectmanagementsystem.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service("InvitationService")
public class InvitationService {
    private final InvitationManager invitationManager;
    private final ProjectManager projectManager;
    private final UserManager userManager;
    private final MemberManager memberManager;

    @Autowired
    InvitationService(InvitationManager invitationManager, ProjectManager projectManager, UserManager userManager, MemberManager memberManager) {
        this.invitationManager = invitationManager;
        this.projectManager = projectManager;
        this.userManager = userManager;
        this.memberManager = memberManager;
    }

    public Response<List<Invitation>> getInvitationList(Integer receiverId) {
        return new Response<>(Response.SUCCESS, "获取成功", invitationManager.findAllByReceiverId(receiverId));
    }

    public Response<String> accept(Integer invitationId) {
        Invitation invitation = invitationManager.findById(invitationId);
        if (invitation == null) {
            return new Response<>(Response.FAIL, "邀请不存在", null);
        }
        Project project = projectManager.findProjectById(invitationManager.findById(invitationId).getProjectId());
        User initiator = userManager.findUserById(invitation.getInitatorId());
        User receiver = userManager.findUserById(invitation.getReceiverId());
        if (project == null || initiator == null || receiver == null) {
            return new Response<>(Response.FAIL, "该项目或邀请人或接受人已注销", null);
        }
        if (!memberManager.isMember(initiator.getId(), project.getId())) {
            return new Response<>(Response.FAIL, "邀请人不在该项目", null);
        }
        if (memberManager.isMember(receiver.getId(), project.getId())) {
            return new Response<>(Response.FAIL, "邀请接受者已在该项目", null);
        }
        memberManager.addMember(project.getId(), receiver.getId());
        invitationManager.delete(invitationId);
        return new Response<>(Response.SUCCESS, "邀请通过，加入项目", null);
    }

    public Response<String> refuse(Integer invitationId) {
        Invitation invitation = invitationManager.findById(invitationId);
        if (invitation == null) {
            return new Response<>(Response.FAIL, "邀请不存在", null);
        }
        invitationManager.delete(invitationId);
        return new Response<>(Response.SUCCESS, "邀请已拒绝，不加入项目", null);
    }
}
