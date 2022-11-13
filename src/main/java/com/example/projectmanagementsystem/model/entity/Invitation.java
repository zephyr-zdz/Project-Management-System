package com.example.projectmanagementsystem.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invitation")
public class Invitation {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "inviter_id", nullable = false)
    private Integer inviterId;
    @Column(name = "receiver_id", nullable = false)
    private Integer receiverId;
    @Column(name = "project_id", nullable = false)
    private Integer projectId;
}
