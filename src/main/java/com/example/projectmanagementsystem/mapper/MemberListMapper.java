package com.example.projectmanagementsystem.mapper;

import com.example.projectmanagementsystem.model.entity.MemberList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberListMapper extends JpaRepository<MemberList, Integer> {
    List<MemberList> findMemberListsByProject_id(Integer member_id);

    List<MemberList> findMemberListsByProject_idAndPrivilege(Integer projectId, String privilege);
}
