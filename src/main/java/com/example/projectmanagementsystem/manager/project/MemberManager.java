package com.example.projectmanagementsystem.manager.project;

import com.example.projectmanagementsystem.mapper.MemberListMapper;
import com.example.projectmanagementsystem.mapper.UserMapper;
import com.example.projectmanagementsystem.model.entity.MemberList;
import com.example.projectmanagementsystem.model.entity.Project;
import com.example.projectmanagementsystem.model.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("ProjectMemberManager")
public class MemberManager {
    private final UserMapper userMapper;
    private final MemberListMapper memberListMapper;

    public MemberManager(UserMapper userMapper, MemberListMapper memberListMapper) {
        this.userMapper = userMapper;
        this.memberListMapper = memberListMapper;
    }

    public List<Integer> findMemberIdListByProjectId(Integer projectId) {// 获取用户id列表
        List<MemberList> projectMemberERList = memberListMapper.findAllByProjectId(projectId);
        List<Integer> projectMemberIdList = new ArrayList<>();
        for (MemberList ERList : projectMemberERList) {
            System.out.println(ERList);
            projectMemberIdList.add(ERList.getMemberId());
        }
        return projectMemberIdList;
    }
    public List<User> findMemberListByProjectId(Integer projectId) {
        List<MemberList> projectMemberERList = memberListMapper.findMemberListsByProjectId(projectId);
        List<User> projectMemberList = new ArrayList<>();
        for (MemberList ERList : projectMemberERList) {
            User member = userMapper.findUserById(ERList.getMemberId());
            projectMemberList.add(member);
        }
        return projectMemberList;
    }

    // manager
    public List<Integer> findManagerIdListByProjectId(Integer projectId) {// 获取管理员id列表
        List<MemberList> projectManagerERList = memberListMapper.findAllByProjectIdAndRole(projectId, "manager");
        List<Integer> projectManagerIdList = new ArrayList<>();
        for (MemberList ERList : projectManagerERList) {
            projectManagerIdList.add(ERList.getMemberId());
        }
        return projectManagerIdList;
    }

    public void saveMember(User member, Project project, String role) {
        Integer memberId = member.getId();
        Integer projectId = project.getId();
        MemberList memberList = new MemberList();
        memberList.initialize(projectId, memberId, role);
        memberListMapper.save(memberList);
    }

    public void deleteProjectMember(Integer project_id, Integer user_id) {
        memberListMapper.deleteByProjectIdAndMemberId(project_id, user_id);
    }

    public boolean isMember(Integer user_id, Integer project_id) {
        return memberListMapper.existsByProjectIdAndMemberId(project_id, user_id);
    }

    public void addMember(Integer projectId, Integer memberId) {
        MemberList memberList = new MemberList();
        memberList.initialize(projectId, memberId, "member");
        memberListMapper.save(memberList);
    }

    public MemberList findMemberListByProjectIdAndUserId(Integer projectId, Integer userId) {
        return memberListMapper.findMemberListByProjectIdAndMemberId(projectId, userId);
    }

    public List<MemberList> findMemberListsByUserId(Integer userId) {
        return memberListMapper.findMemberListsByMemberId(userId);
    }
}
