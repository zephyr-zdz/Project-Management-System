package com.example.projectmanagementsystem.mapper;

import com.example.projectmanagementsystem.model.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueMapper extends JpaRepository<Issue, Integer> {
    Issue findIssueById(Integer id);

    List<Issue> findIssuesByProjectId(Integer projectId);

    List<Issue> findIssuesByAssigneeId(Integer userId);

    List<Issue> findIssuesByReviewerId(Integer userId);

    List<Issue> findIssuesByAssigneeIdAndProjectId(Integer userId, Integer projectId);

    List<Issue> findIssuesByReviewerIdAndProjectId(Integer userId, Integer projectId);

    List<Issue> findIssuesByAssigneeIdAndProjectIdAndStatus(Integer userId, Integer projectId, String status);

    List<Issue> findIssuesByReviewerIdAndProjectIdAndStatus(Integer userId, Integer projectId, String status);

    List<Issue> findIssuesByAssigneeIdAndStatus(Integer userId, String status);

    List<Issue> findIssuesByReviewerIdAndStatus(Integer userId, String status);

    List<Issue> findIssuesByProjectIdAndStatus(Integer projectId, String status);

    List<Issue> findIssuesByStatus(String status);
}
