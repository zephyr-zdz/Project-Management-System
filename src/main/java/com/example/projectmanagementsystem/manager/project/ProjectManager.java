package com.example.projectmanagementsystem.manager.project;

import com.example.projectmanagementsystem.mapper.ProjectMapper;
import com.example.projectmanagementsystem.model.ClassAdapter;
import com.example.projectmanagementsystem.model.entity.Project;
import com.example.projectmanagementsystem.model.vo.ProjectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ProjectProjectManager")
public class ProjectManager {
    private final ProjectMapper projectMapper;
    private final ClassAdapter classAdapter;

    @Autowired
    ProjectManager(ProjectMapper projectMapper, ClassAdapter classAdapter) {
        this.projectMapper = projectMapper;
        this.classAdapter = classAdapter;
    }

    public ProjectVO findProjectListById(Integer id) {
        return classAdapter.fromProject2ProjectVO(findProjectById(id));
    }

    public Project findProjectById(Integer id) {
        return projectMapper.findProjectById(id);
    }

    public void createProject(Project project) {
        projectMapper.save(project);
    }
}
