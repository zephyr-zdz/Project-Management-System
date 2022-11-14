package com.example.projectmanagementsystem.mapper;

import com.example.projectmanagementsystem.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectMapper extends JpaRepository<Project, Integer> {

    Project findProjectById(Integer id);

    void update(Project project);
}
