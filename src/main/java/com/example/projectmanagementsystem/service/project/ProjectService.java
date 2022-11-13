package com.example.projectmanagementsystem.service.project;

import com.example.projectmanagementsystem.manager.InvitationManager;
import com.example.projectmanagementsystem.manager.project.MemberManager;
import com.example.projectmanagementsystem.manager.project.ProjectManager;
import com.example.projectmanagementsystem.manager.user.UserManager;
import com.example.projectmanagementsystem.model.ClassAdapter;
import com.example.projectmanagementsystem.model.entity.Invitation;
import com.example.projectmanagementsystem.model.entity.MemberList;
import com.example.projectmanagementsystem.model.entity.Project;
import com.example.projectmanagementsystem.model.entity.User;
import com.example.projectmanagementsystem.model.vo.ProjectVO;
import com.example.projectmanagementsystem.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("ProjectProjectService")
public class ProjectService {
    private final ProjectManager projectManager;
    private final MemberManager memberManager;
    private final UserManager userManager;
    private final InvitationManager invitationManager;
    private final ClassAdapter classAdapter;

    @Autowired
    ProjectService(ProjectManager projectManager, MemberManager memberManager, UserManager userManager, InvitationManager invitationManager, ClassAdapter classAdapter) {
        this.projectManager = projectManager;
        this.memberManager = memberManager;
        this.userManager = userManager;
        this.invitationManager = invitationManager;
        this.classAdapter = classAdapter;
    }

    public Response<Project> create(Project project) {
        if (project.getId() != null) {
            return new Response<>(Response.FAIL, "项目id由系统自动生成", null);
        }
        User owner = userManager.findUserById(project.getOwner_id());
        if (owner == null) {
            return new Response<>(Response.FAIL, "项目创建者不存在！", null);
        }
        projectManager.createProject(project);
        memberManager.saveMember(owner, project, "owner");
        return new Response<>(Response.SUCCESS, "项目信息注册成功", project);
    }

    public Response<ProjectVO> list(Integer projectId) {
        Project project = projectManager.findProjectById(projectId);
        if (project == null) {
            return new Response<>(Response.FAIL, "该项目不存在", null);
        }
        ProjectVO projectVO = classAdapter.fromProject2ProjectVO(project);
        return new Response<>(Response.SUCCESS, "项目信息查找成功", projectVO);
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

    public Response<List<ProjectVO>> getParticipating(Integer userId) {
        User user = userManager.findUserById(userId);
        if (user == null) {
            return new Response<>(Response.FAIL, "用户不存在", null);
        }
        List<MemberList> ERList = memberManager.findMemberListsByUserId(userId);
        List<ProjectVO> projectVOs = new ArrayList<>();
        for (MemberList ER : ERList) {
            Project project = projectManager.findProjectById(ER.getProjectId());
            projectVOs.add(classAdapter.fromProject2ProjectVO(project));
        }
        return new Response<>(Response.SUCCESS, "查找成功", projectVOs);
    }
}
