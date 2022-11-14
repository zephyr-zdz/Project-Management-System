package com.example.projectmanagementsystem.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "member_list")
public class MemberList {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "member_id", nullable = false)
    private Integer memberId;
    @Column(name = "project_id", nullable = false)
    private Integer projectId;
    @Column(name = "role", nullable = false)
    private String role;

    public void initialize(Integer project_id, Integer member_id, String role) {
        this.memberId = member_id;
        this.projectId = project_id;
        this.role = role;
    }
}
