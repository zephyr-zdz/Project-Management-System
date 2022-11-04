package com.example.projectmanagementsystem.model;

import com.example.projectmanagementsystem.mapper.UserMapper;
import com.example.projectmanagementsystem.model.entity.User;
import com.example.projectmanagementsystem.model.vo.SafeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ClassAdapter")
public class ClassAdapter {
    @Autowired
    public ClassAdapter() {
    }

    public SafeUser fromUser2SafeUser(User user) {
        SafeUser safeUser = new SafeUser();
        safeUser.setId(user.getId());
        safeUser.setEmail(user.getEmail());
        safeUser.setUsername(user.getUsername());
        return safeUser;
    }
}
