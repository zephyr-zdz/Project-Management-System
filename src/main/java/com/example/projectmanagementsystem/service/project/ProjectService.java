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
    private final ClassAdapter classAdapter;

    @Autowired
    ProjectService(ProjectManager projectManager, MemberManager memberManager, UserManager userManager, ClassAdapter classAdapter) {
        this.projectManager = projectManager;
        this.memberManager = memberManager;
        this.userManager = userManager;
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
}
