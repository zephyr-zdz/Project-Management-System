package com.example.projectmanagementsystem.service;

import com.example.projectmanagementsystem.manager.InvitationManager;
import com.example.projectmanagementsystem.manager.project.MemberManager;
import com.example.projectmanagementsystem.manager.project.ProjectManager;
import com.example.projectmanagementsystem.manager.user.UserManager;
import com.example.projectmanagementsystem.model.ClassAdapter;
import com.example.projectmanagementsystem.model.entity.Invitation;
import com.example.projectmanagementsystem.model.entity.Project;
import com.example.projectmanagementsystem.model.entity.User;
import com.example.projectmanagementsystem.model.vo.InvitationVO;
import com.example.projectmanagementsystem.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service("InvitationService")
public class InvitationService {
    private final InvitationManager invitationManager;
    private final ProjectManager projectManager;
    private final UserManager userManager;
    private final MemberManager memberManager;
    private final ClassAdapter classAdapter;

    @Autowired
    InvitationService(InvitationManager invitationManager, ProjectManager projectManager, UserManager userManager, MemberManager memberManager, ClassAdapter classAdapter) {
        this.invitationManager = invitationManager;
        this.projectManager = projectManager;
        this.userManager = userManager;
        this.memberManager = memberManager;
        this.classAdapter = classAdapter;
    }

    public Response<String> invite(Integer inviterId, Integer receiverId, Integer projectId) {
        User inviter = userManager.findUserById(inviterId);
        User receiver = userManager.findUserById(receiverId);
        Project project = projectManager.findProjectById(projectId);
        if (inviter == null || receiver == null || project == null) {
            return new Response<>(Response.FAIL, "邀请失败，用户或项目不存在", null);
        }
        if (!memberManager.isMember(inviterId, projectId)) {
            return new Response<>(Response.FAIL, "邀请失败，您不是该项目成员", null);
        }
        if (memberManager.isMember(receiverId, projectId)) {
            return new Response<>(Response.FAIL, "邀请失败，该用户已是该项目成员", null);
        }
        Invitation invitation = new Invitation();
        invitation.setInviterId(inviterId);
        invitation.setReceiverId(receiverId);
        invitation.setProjectId(projectId);
        invitationManager.save(invitation);
        return new Response<>(Response.SUCCESS, "邀请成功", null);
    }

    public Response<List<InvitationVO>> getInvitationList(Integer receiverId) {
        List<Invitation> invitationList = invitationManager.findAllByReceiverId(receiverId);
        List<InvitationVO> invitationVOList = new ArrayList<>();
        for (Invitation invitation : invitationList) {
            invitationVOList.add(classAdapter.fromInvitation2InvitationVO(invitation));
        }
        return new Response<>(Response.SUCCESS, "获取成功", invitationVOList);
    }

    public Response<String> accept(Integer invitationId) {
        Invitation invitation = invitationManager.findById(invitationId);
        if (invitation == null) {
            return new Response<>(Response.FAIL, "邀请不存在", null);
        }
        Project project = projectManager.findProjectById(invitationManager.findById(invitationId).getProjectId());
        User inviter = userManager.findUserById(invitation.getInviterId());
        User receiver = userManager.findUserById(invitation.getReceiverId());
        if (project == null || inviter == null || receiver == null) {
            return new Response<>(Response.FAIL, "该项目或邀请人或接受人已注销", null);
        }
        if (!memberManager.isMember(inviter.getId(), project.getId())) {
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
