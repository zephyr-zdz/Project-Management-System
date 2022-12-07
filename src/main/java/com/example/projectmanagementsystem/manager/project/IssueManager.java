package com.example.projectmanagementsystem.manager.project;

import com.example.projectmanagementsystem.mapper.IssueMapper;
import com.example.projectmanagementsystem.model.entity.Issue;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("ProjectIssueManager")
public class IssueManager {
    private final IssueMapper issueMapper;

    public IssueManager(IssueMapper issueMapper) {
        this.issueMapper = issueMapper;
    }

    public void createIssue(Issue issue) {
        issue.initialize(issue.getProjectId(), issue.getReviewerId(), issue.getAssigneeId(), issue.getTitle(), issue.getDescription());
        issueMapper.save(issue);
    }

    public Issue findIssueById(Integer id) {
        return issueMapper.findIssueById(id);
    }

    public void editIssue(Issue issue) {
        issueMapper.save(issue);
    }

    public List<Issue> findIssuesByProjectId(Integer projectId) {
        return issueMapper.findIssuesByProjectId(projectId);
    }

    public List<Issue> findIssuesByAssigneeId(Integer userId) {
        return issueMapper.findIssuesByAssigneeId(userId);
    }

    public List<Issue> findIssuesByReviewerId(Integer userId) { return issueMapper.findIssuesByReviewerId(userId); }
}
