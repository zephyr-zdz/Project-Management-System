package com.example.projectmanagementsystem.manager.project;

import com.example.projectmanagementsystem.mapper.ProjectMapper;
import com.example.projectmanagementsystem.model.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ProjectProjectManager")
public class ProjectManager {
    private final ProjectMapper projectMapper;

    @Autowired
    ProjectManager(ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }


    public Project findProjectById(Integer id) {
        return projectMapper.findProjectById(id);
    }

    public void createProject(Project project) {
        projectMapper.save(project);
    }

    public void editProject(Project project) { projectMapper.update(project); }
}
