package com.example.projectmanagementsystem.model;

import com.example.projectmanagementsystem.manager.project.MemberManager;
import com.example.projectmanagementsystem.manager.project.ProjectManager;
import com.example.projectmanagementsystem.manager.user.UserManager;
import com.example.projectmanagementsystem.model.entity.Invitation;
import com.example.projectmanagementsystem.model.entity.Project;
import com.example.projectmanagementsystem.model.entity.User;
import com.example.projectmanagementsystem.model.vo.InvitationVO;
import com.example.projectmanagementsystem.model.vo.ProjectVO;
import com.example.projectmanagementsystem.model.vo.SafeUser;
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
        // manager
        Integer projectId = project.getId();
        List<User> managerList = new ArrayList<>(memberManager.findManagerListByProjectId(projectId));
        projectVO.setManagerList(managerList);
        // member
        List<User> memberList = new ArrayList<>(memberManager.findMemberListByProjectId(projectId));
        projectVO.setMemberList(memberList);
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
}
