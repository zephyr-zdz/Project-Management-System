package com.example.projectmanagementsystem.service.project;


import com.example.projectmanagementsystem.manager.project.MemberManager;
import com.example.projectmanagementsystem.manager.project.ProjectManager;
import com.example.projectmanagementsystem.manager.user.UserManager;
import com.example.projectmanagementsystem.model.entity.Project;
import com.example.projectmanagementsystem.model.entity.User;
import com.example.projectmanagementsystem.util.Response;
import org.springframework.stereotype.Service;

@Service("ProjectMemberService")
public class MemberService {
    private final ProjectManager projectManager;
    private final MemberManager memberManager;
    private final UserManager userManager;

    public MemberService(ProjectManager projectManager, MemberManager memberManager, UserManager userManager) {
        this.projectManager = projectManager;
        this.memberManager = memberManager;
        this.userManager = userManager;
    }

    public Response<String> inviteMember(Integer owner_id, Integer project_id, Integer new_member_id) {
        User owner = userManager.findUserById(owner_id);
        if (owner == null) {
            return new Response<>(Response.FAIL, "项目所有者不存在！", null);
        }
        User newMember = userManager.findUserById(new_member_id);
        if (newMember == null) {
            return new Response<>(Response.FAIL, "被邀请人不存在！", null);
        }
        Project project = projectManager.findProjectById(project_id);
        if (project == null) {
            return new Response<>(Response.FAIL, "项目不存在！", null);
        }
        // TODO：保存邀请
        return new Response<>(Response.SUCCESS, "邀请保存成功！", null);
    }
}
