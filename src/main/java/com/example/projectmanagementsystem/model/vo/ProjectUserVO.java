package com.example.projectmanagementsystem.model.vo;

import com.example.projectmanagementsystem.model.entity.Project;
import com.example.projectmanagementsystem.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectUserVO {
    private Project project;
    private SafeUser owner;
    private String role;
    private List<User> managerList;
    private List<User> memberList;
    private Integer number; // 项目总人数
}
