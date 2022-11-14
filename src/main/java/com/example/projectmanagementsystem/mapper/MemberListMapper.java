package com.example.projectmanagementsystem.mapper;

import com.example.projectmanagementsystem.model.entity.MemberList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberListMapper extends JpaRepository<MemberList, Integer> {
    List<MemberList> findMemberListsByProjectId(Integer projectId);

    List<MemberList> findAllByProjectIdAndRole(Integer projectId, String role);

    List<MemberList> findAllByProjectId(Integer projectId);

    void deleteByProjectIdAndMemberId(Integer project_id, Integer user_id);

    boolean existsByProjectIdAndMemberId(Integer project_id, Integer user_id);

    MemberList findMemberListByProjectIdAndMemberId(Integer projectId, Integer userId);

    List<MemberList> findMemberListsByMemberId(Integer userId);
}
