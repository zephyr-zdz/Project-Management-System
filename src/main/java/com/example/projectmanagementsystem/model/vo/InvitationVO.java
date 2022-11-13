package com.example.projectmanagementsystem.model.vo;

import com.example.projectmanagementsystem.model.entity.Project;
import com.example.projectmanagementsystem.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvitationVO {
    private Integer id;
    private User inviter;
    private User receiver;
    private Project project;
}
