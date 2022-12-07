package com.example.projectmanagementsystem.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleUser {
    private Integer id;
    private String email;
    private String username;
    private String role;
}

