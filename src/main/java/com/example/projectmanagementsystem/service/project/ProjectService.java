package com.example.projectmanagementsystem.service.project;

import com.example.projectmanagementsystem.manager.project.ProjectManager;
import com.example.projectmanagementsystem.model.entity.Project;
import com.example.projectmanagementsystem.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ProjectProjectService")
public class ProjectService {
    private final ProjectManager projectManager;

    @Autowired
    ProjectService(ProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    public Response<Project> create(Project project) {
        if (project.getId() != null) {
            return new Response<>(Response.FAIL, "用户id由系统自动生成", null);
        }

        projectManager.createProject(project);
        return new Response<>(Response.SUCCESS, "项目信息注册成功", project);
    }
}
