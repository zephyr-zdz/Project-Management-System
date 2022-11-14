package com.example.projectmanagementsystem.controller.project;

import com.example.projectmanagementsystem.model.entity.Project;
import com.example.projectmanagementsystem.model.vo.ProjectVO;
import com.example.projectmanagementsystem.service.InvitationService;
import com.example.projectmanagementsystem.service.project.ProjectService;
import com.example.projectmanagementsystem.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("ProjectController")
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;
    private final InvitationService invitationService;

    @Autowired
    ProjectController(ProjectService projectService, InvitationService invitationService) {
        this.projectService = projectService;
        this.invitationService = invitationService;
    }

    @PostMapping("/create")
    public Response<ProjectVO> createProject(@RequestBody Project project) {
        return projectService.create(project);
    }

    @PostMapping("/edit")
    public Response<ProjectVO> editProject(@RequestBody Project project) {
        return projectService.edit(project);
    }

    @GetMapping("/list")
    public Response<ProjectVO> getProjectList(@RequestParam("project_id") Integer projectId) {
        return projectService.list(projectId);
    }
    @PostMapping("/invite")
    public Response<String> invite(@RequestParam("user_id") Integer user_id,
                                   @RequestParam("receiver_id") Integer receiver_id,
                                   @RequestParam("project_id") Integer project_id) {
        return invitationService.invite(user_id, receiver_id, project_id);
    }
}
