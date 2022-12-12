package com.example.projectmanagementsystem.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueVO {
    private Integer id;
    private String projectName;
    private String reviewerName;
    private String assigneeName;
    private String title;
    private String description;
    private String status;
    private String date;
}
