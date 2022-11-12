package com.example.projectmanagementsystem.manager.project;

import com.example.projectmanagementsystem.mapper.MemberListMapper;
import com.example.projectmanagementsystem.mapper.ProjectMapper;
import com.example.projectmanagementsystem.mapper.UserMapper;
import com.example.projectmanagementsystem.model.ClassAdapter;
import com.example.projectmanagementsystem.model.entity.MemberList;
import com.example.projectmanagementsystem.model.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("ProjectMemberManager")
public class MemberManager {
    private final ProjectMapper projectMapper;
    private final UserMapper userMapper;
    private final MemberListMapper memberListMapper;
    private final ClassAdapter classAdapter;

    public MemberManager(ProjectMapper projectMapper, UserMapper userMapper, MemberListMapper memberListMapper, ClassAdapter classAdapter) {
        this.projectMapper = projectMapper;
        this.userMapper = userMapper;
        this.memberListMapper = memberListMapper;
        this.classAdapter = classAdapter;
    }

    public List<Integer> findMemberIdListByProjectId(Integer projectId) {// 获取用户id列表
        List<MemberList> projectMemberERList = memberListMapper.findMemberListsByProject_id(projectId);
        List<Integer> projectMemberIdList = new ArrayList<>();
        for (MemberList ERList : projectMemberERList) {
            projectMemberIdList.add(ERList.getMember_id());
        }
        return projectMemberIdList;
    }

    public List<User> findMemberListByProjectId(Integer projectId) {
        List<MemberList> projectMemberERList = memberListMapper.findMemberListsByProject_id(projectId);
        List<User> projectMemberList = new ArrayList<>();
        for (MemberList ERList : projectMemberERList) {
            User member = userMapper.findUserById(ERList.getMember_id());
            projectMemberList.add(member);
        }
        return projectMemberList;
    }

    // manager
    public List<Integer> findManagerIdListByProjectId(Integer projectId) {// 获取用户id列表
        List<MemberList> projectMemberERList = memberListMapper.findMemberListsByProject_idAndPrivilege(projectId, "manager");
        List<Integer> projectManagerIdList = new ArrayList<>();
        for (MemberList ERList : projectMemberERList) {
            projectManagerIdList.add(ERList.getMember_id());
        }
        return projectManagerIdList;
    }
}
