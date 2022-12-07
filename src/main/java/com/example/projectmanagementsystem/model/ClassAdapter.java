package com.example.projectmanagementsystem.model;

import com.example.projectmanagementsystem.manager.project.MemberManager;
import com.example.projectmanagementsystem.manager.project.ProjectManager;
import com.example.projectmanagementsystem.manager.user.UserManager;
import com.example.projectmanagementsystem.model.entity.Invitation;
import com.example.projectmanagementsystem.model.entity.Project;
import com.example.projectmanagementsystem.model.entity.User;
import com.example.projectmanagementsystem.model.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("ClassAdapter")
public class ClassAdapter {
    private final MemberManager memberManager;
    private final ProjectManager projectManager;
    private final UserManager userManager;

    @Autowired
    public ClassAdapter(MemberManager memberManager, ProjectManager projectManager, UserManager userManager) {
        this.memberManager = memberManager;
        this.projectManager = projectManager;
        this.userManager = userManager;
    }

    public SafeUser fromUser2SafeUser(User user) {
        SafeUser safeUser = new SafeUser();
        safeUser.setId(user.getId());
        safeUser.setEmail(user.getEmail());
        safeUser.setUsername(user.getUsername());
        return safeUser;
    }

    public ProjectVO fromProject2ProjectVO(Project project) {
        ProjectVO projectVO = new ProjectVO();
        projectVO.setProject(project);
        projectVO.setOwner(fromUser2SafeUser(userManager.findUserById(project.getOwner_id())));
        // manager
        Integer projectId = project.getId();
        List<User> managerList = new ArrayList<>(memberManager.findManagerListByProjectId(projectId));
        projectVO.setManagerList(managerList);
        // member
        List<User> memberList = memberManager.findMembersByProjectId(projectId);
        List<RoleUser> memberRoleList = new ArrayList<>();
        for (User member : memberList) {
            memberRoleList.add(fromUser2RoleUser(project,member));
        }
        projectVO.setMemberList(memberRoleList);
        // number
        projectVO.setNumber(memberList.size());
        return projectVO;
    }

    public InvitationVO fromInvitation2InvitationVO(Invitation invitation) {
        InvitationVO invitationVO = new InvitationVO();
        invitationVO.setId(invitation.getId());
        invitationVO.setProject(projectManager.findProjectById(invitation.getProjectId()));
        invitationVO.setInviter(userManager.findUserById(invitation.getInviterId()));
        invitationVO.setReceiver(userManager.findUserById(invitation.getReceiverId()));
        return invitationVO;
    }

    public ProjectUserVO fromProject2ProjectUserVO(Project project, User user) {
        ProjectUserVO projectUserVO = new ProjectUserVO();
        projectUserVO.setProject(project);
        String role = memberManager.findMemberListByProjectIdAndUserId(project.getId(), user.getId()).getRole();
        projectUserVO.setRole(role);
        projectUserVO.setOwner(fromUser2SafeUser(userManager.findUserById(project.getOwner_id())));
        // member
        List<User> memberList = memberManager.findMembersByProjectId(project.getId());
        List<RoleUser> memberRoleList = new ArrayList<>();
        for (User member : memberList) {
            memberRoleList.add(fromUser2RoleUser(project,member));
        }
        projectUserVO.setMemberList(memberRoleList);
        // number
        projectUserVO.setNumber(memberList.size());
        return projectUserVO;
    }
    public RoleUser fromUser2RoleUser(Project project, User user) {
        RoleUser roleUser = new RoleUser();
        roleUser.setId(user.getId());
        roleUser.setEmail(user.getEmail());
        roleUser.setUsername(user.getUsername());
        String role = memberManager.findMemberListByProjectIdAndUserId(project.getId(), user.getId()).getRole();
        roleUser.setRole(role);
        return roleUser;
    }
}
