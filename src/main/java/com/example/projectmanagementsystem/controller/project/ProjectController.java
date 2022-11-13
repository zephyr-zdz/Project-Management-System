package com.example.projectmanagementsystem.controller.project;

import com.example.projectmanagementsystem.model.entity.Project;
import com.example.projectmanagementsystem.model.vo.ProjectVO;
import com.example.projectmanagementsystem.service.project.ProjectService;
import com.example.projectmanagementsystem.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("ProjectController")
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;

    @Autowired
    ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/create")
    public Response<Project> createProject(@RequestBody Project project) {
        return projectService.create(project);
    }

    @GetMapping("/list")
    public Response<ProjectVO> getProjectList(@RequestParam("project_id") Integer projectId) {
        return projectService.list(projectId);
    }
    @PostMapping("/invite")
    public Response<String> invite(@RequestParam("user_id") Integer user_id,
                                   @RequestParam("receiver_id") Integer receiver_id,
                                   @RequestParam("project_id") Integer project_id) {
        return projectService.invite(user_id, receiver_id, project_id);
    }
}
