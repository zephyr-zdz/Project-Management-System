package com.example.projectmanagementsystem.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "message")
public class Message {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @Column(name = "issue_id", nullable = false)
    private Integer issueId;
    @Column(name = "seen", nullable = false)
    private Boolean seen;
    public void initialize(Integer user_id, Integer issue_id) {
        this.userId = user_id;
        this.issueId = issue_id;
        this.seen = false;
    }
}
