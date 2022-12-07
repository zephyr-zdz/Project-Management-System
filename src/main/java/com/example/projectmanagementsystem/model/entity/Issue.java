package com.example.projectmanagementsystem.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "issue")
public class Issue {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "project_id", nullable = false)
    private Integer projectId;
    @Column(name = "reviewer_id", nullable = false)
    private Integer reviewerId;
    @Column(name = "assignee_id", nullable = false)
    private Integer assigneeId;
    @Column(name = "title", nullable = false, length = 30)
    private String title;
    @Column(name = "description", length = 3000)
    private String description;
    @Column(name = "status", nullable = false)
    private String status;

    public void initialize(Integer project_id, Integer reviewer_id, Integer assignee_id, String title, String description) {
        this.projectId = project_id;
        this.reviewerId = reviewer_id;
        this.assigneeId = assignee_id;
        this.title = title;
        this.description = description;
        this.status = "open";
    }
}
