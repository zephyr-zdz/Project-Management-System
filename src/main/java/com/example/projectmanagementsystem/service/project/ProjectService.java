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
import java.util.Objects;

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

    public Response<ProjectVO> create(Project project) {
        if (project.getId() != null) {
            return new Response<>(Response.FAIL, "项目id由系统自动生成", null);
        }
        User owner = userManager.findUserById(project.getOwner_id());
        if (owner == null) {
            return new Response<>(Response.FAIL, "项目创建者不存在！", null);
        }
        projectManager.createProject(project);
        memberManager.saveMember(owner, project, "owner");
        ProjectVO projectVO = classAdapter.fromProject2ProjectVO(project);
        return new Response<>(Response.SUCCESS, "项目信息注册成功", projectVO);
    }

    public Response<ProjectVO> list(Integer projectId) {
        Project project = projectManager.findProjectById(projectId);
        if (project == null) {
            return new Response<>(Response.FAIL, "该项目不存在", null);
        }
        ProjectVO projectVO = classAdapter.fromProject2ProjectVO(project);
        return new Response<>(Response.SUCCESS, "项目信息查找成功", projectVO);
    }

    public Response<ProjectVO> edit(Project project) {
        Project oldProject = projectManager.findProjectById(project.getId());
        if (oldProject == null) {
            return new Response<>(Response.FAIL, "该项目不存在", null);
        }
        String msg = "";
        // 项目所有者
        if (userManager.findUserById(project.getOwner_id()) == null) {
            return new Response<>(Response.FAIL, "项目创建者不存在！", null);
        } else if (memberManager.isMember(project.getOwner_id(), project.getId())) {
            return new Response<>(Response.FAIL, "新项目创建者为项目成员！", null);
        }
        else if (!Objects.equals(project.getOwner_id(), oldProject.getOwner_id())) {
            return new Response<>(Response.FAIL, "项目创建者不可修改！", null);
        }
        // 项目标题
        if (project.getTitle() == null || project.getTitle().equals("")) {
            return new Response<>(Response.FAIL, "项目标题不能为空", null);
        } else if (!Objects.equals(project.getTitle(), oldProject.getTitle())) { // 不相等
            msg += "项目名称更改成功；";
        }
        // 项目描述
        if (!Objects.equals(project.getIntro(), oldProject.getIntro())) { // 不相等
            msg += "项目描述更改成功；";
        }
        projectManager.editProject(project);
        ProjectVO projectVO = classAdapter.fromProject2ProjectVO(project);
        return new Response<>(Response.SUCCESS, msg+"项目信息修改成功。", projectVO);
    }

    public Response<String> transfer(Integer new_owner_id, Integer project_id) {
        User newOwner = userManager.findUserById(new_owner_id);
        Project project = projectManager.findProjectById(project_id);
        User oldOwner = userManager.findUserById(project.getOwner_id());
        if (newOwner == null) {
            return new Response<>(Response.FAIL, "新项目创建者不存在！", null);
        } else if (Objects.equals(new_owner_id, oldOwner.getId())) {
            return new Response<>(Response.INVALID_PARAMETER, "新项目创建者为原项目创建者！", null);
        }
        if (!memberManager.isMember(new_owner_id, project_id)) {
            memberManager.saveMember(newOwner, project, "member");
        }
        memberManager.editRole(oldOwner.getId(), project_id, "member");
        memberManager.editRole(new_owner_id, project_id, "owner");
        project.setOwner_id(new_owner_id);
        projectManager.editProject(project);
        return new Response<>(Response.SUCCESS, "项目创建者更改成功", null);
    }
}
