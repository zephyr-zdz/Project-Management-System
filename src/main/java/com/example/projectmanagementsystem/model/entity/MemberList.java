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
    private Integer member_id;
    @Column(name = "project_id", nullable = false)
    private Integer project_id;
    @Column(name = "privilege", nullable = false)
    private String privilege;

    public void initialize(Integer member_id, Integer project_id) {
        this.member_id = member_id;
        this.project_id = project_id;
        this.privilege = "normal";
    }
}
