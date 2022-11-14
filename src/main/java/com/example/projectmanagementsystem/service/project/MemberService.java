package com.example.projectmanagementsystem.service.project;


import com.example.projectmanagementsystem.manager.project.MemberManager;
import com.example.projectmanagementsystem.manager.project.ProjectManager;
import com.example.projectmanagementsystem.manager.user.UserManager;
import com.example.projectmanagementsystem.model.ClassAdapter;
import com.example.projectmanagementsystem.model.entity.MemberList;
import com.example.projectmanagementsystem.model.entity.Project;
import com.example.projectmanagementsystem.model.entity.User;
import com.example.projectmanagementsystem.model.vo.ProjectVO;
import com.example.projectmanagementsystem.util.Response;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Transactional
@Service("ProjectMemberService")
public class MemberService {
    private final ProjectManager projectManager;
    private final MemberManager memberManager;
    private final UserManager userManager;
    private final ClassAdapter classAdapter;


    public MemberService(ProjectManager projectManager, MemberManager memberManager, UserManager userManager, ClassAdapter classAdapter) {
        this.projectManager = projectManager;
        this.memberManager = memberManager;
        this.userManager = userManager;
        this.classAdapter = classAdapter;
    }

    public Response<String> quit(Integer project_id, Integer user_id) {
        User user = userManager.findUserById(user_id);
        if (user == null) {
            return new Response<>(Response.FAIL, "用户不存在！", null);
        }
        Project project = projectManager.findProjectById(project_id);
        if (project == null) {
            return new Response<>(Response.FAIL, "项目不存在！", null);
        }
        if (Objects.equals(project.getOwner_id(), user_id)) {
            return new Response<>(Response.FAIL, "用户为所有者！", null);
        }
        memberManager.deleteProjectMember(project_id, user_id);
        return new Response<>(Response.SUCCESS, "删除成功！", null);
    }

    public Response<List<ProjectVO>> findProjects(Integer userId) {
        User user = userManager.findUserById(userId);
        if (user == null) {
            return new Response<>(Response.FAIL, "用户不存在", null);
        }
        List<Integer> projectIdList = memberManager.findProjectIdListByUserId(userId);
        List<ProjectVO> projectVOs = new ArrayList<>();
        for (Integer projectId : projectIdList) {
            Project project = projectManager.findProjectById(projectId);
            projectVOs.add(classAdapter.fromProject2ProjectVO(project));
        }
        return new Response<>(Response.SUCCESS, "查找成功", projectVOs);
    }

    public Response<String> getRole(Integer project_id, Integer user_id) {
        Project project = projectManager.findProjectById(project_id);
        User user = userManager.findUserById(user_id);
        if (project == null) {
            return new Response<>(Response.FAIL, "项目不存在", null);
        }
        if (user == null) {
            return new Response<>(Response.FAIL, "用户不存在", null);
        }
        MemberList ER = memberManager.findMemberListByProjectIdAndUserId(project_id, user_id);
        if (ER == null) {
            return new Response<>(Response.NOT_FOUND, "用户不在项目中", null);
        }
        return new Response<>(Response.SUCCESS, "查找成功", ER.getRole());
    }

    public Response<String> editRole(Integer project_id, Integer user_id, String role) {
        if (role == null) {
            return new Response<>(Response.FAIL, "角色不能为空", null);
        }
        if (role.equals("owner")) {
            return new Response<>(Response.FAIL, "所有者角色不可更改", null);
        }
        if (!role.equals("member") && !role.equals("manager")) {
            return new Response<>(Response.FAIL, "角色不合法", null);
        }
        Project project = projectManager.findProjectById(project_id);
        User user = userManager.findUserById(user_id);
        if (project == null) {
            return new Response<>(Response.FAIL, "项目不存在", null);
        }
        if (user == null) {
            return new Response<>(Response.FAIL, "用户不存在", null);
        }
        MemberList ER = memberManager.findMemberListByProjectIdAndUserId(project_id, user_id);
        if (ER == null) {
            return new Response<>(Response.FAIL, "用户不在项目中", null);
        }
        if (role.equals(ER.getRole())) {
            return new Response<>(Response.INVALID_PARAMETER, "用户权限未更改", null);
        }
        memberManager.editRole(ER.getMemberId(), ER.getProjectId(), role);
        return new Response<>(Response.SUCCESS, "修改成功", role);
    }
}
