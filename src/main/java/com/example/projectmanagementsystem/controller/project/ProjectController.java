package com.example.projectmanagementsystem.controller.project;

import com.example.projectmanagementsystem.model.entity.Project;
import com.example.projectmanagementsystem.service.project.ProjectService;
import com.example.projectmanagementsystem.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        System.out.println(project);
        return projectService.create(project);
    }
}
