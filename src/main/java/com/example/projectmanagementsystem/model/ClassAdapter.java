package com.example.projectmanagementsystem.model;

import com.example.projectmanagementsystem.manager.project.MemberManager;
import com.example.projectmanagementsystem.model.entity.Project;
import com.example.projectmanagementsystem.model.entity.User;
import com.example.projectmanagementsystem.model.vo.ProjectVO;
import com.example.projectmanagementsystem.model.vo.SafeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("ClassAdapter")
public class ClassAdapter {
    private final MemberManager memberManager;

    @Autowired
    public ClassAdapter(MemberManager memberManager) {
        this.memberManager = memberManager;
    }

    public SafeUser fromUser2SafeUser(User user) {
        SafeUser safeUser = new SafeUser();
        safeUser.setId(user.getId());
        safeUser.setEmail(user.getEmail());
        safeUser.setUsername(user.getUsername());
        return safeUser;
    }

    public ProjectVO fromProject2ProjectVO(Project project) {
        ProjectVO projectVO = new ProjectVO();
        projectVO.setProject(project);
        // manager
        Integer projectId = project.getId();
        List<Integer> managerIdList = new ArrayList<>(memberManager.findManagerIdListByProjectId(projectId));
        projectVO.setManagerIdList(managerIdList);
        // member
        List<Integer> memberIdList = new ArrayList<>(memberManager.findMemberIdListByProjectId(projectId));
        projectVO.setMemberIdList(memberIdList);
        // number
        projectVO.setNumber(memberIdList.size());
        return projectVO;
    }
}
