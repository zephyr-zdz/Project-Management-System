package com.example.projectmanagementsystem.model.vo;

import com.example.projectmanagementsystem.model.entity.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectVO {
    private Project project;
    private List<Integer> managerIdList;
    private List<Integer> memberIdList;
    private Integer number; // 项目总人数
}
