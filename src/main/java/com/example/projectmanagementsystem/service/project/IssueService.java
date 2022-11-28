package com.example.projectmanagementsystem.service.project;

import com.example.projectmanagementsystem.manager.project.IssueManager;
import com.example.projectmanagementsystem.manager.project.ProjectManager;
import com.example.projectmanagementsystem.manager.user.UserManager;
import com.example.projectmanagementsystem.model.ClassAdapter;
import com.example.projectmanagementsystem.model.entity.Issue;
import com.example.projectmanagementsystem.util.Response;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service("ProjectIssueService")
public class IssueService {
    private final IssueManager issueManager;
    private final ProjectManager projectManager;
    private final UserManager userManager;
    private final ClassAdapter classAdapter;

    public IssueService(IssueManager issueManager, ProjectManager projectManager, UserManager userManager, ClassAdapter classAdapter) {
        this.issueManager = issueManager;
        this.projectManager = projectManager;
        this.userManager = userManager;
        this.classAdapter = classAdapter;
    }
    public Response<Issue> create(Issue issue) {
        if (issue == null) {
            return new Response<>(Response.FAIL, "issue为空", null);
        }
        if (projectManager.findProjectById(issue.getProjectId()) == null) {
            return new Response<>(Response.FAIL, "issue所属项目为空", null);
        }
        if (userManager.findUserById(issue.getReviewerId()) == null) {
            return new Response<>(Response.FAIL, "issue指派者为空", null);
        }
        if (userManager.findUserById(issue.getAssigneeId()) == null) {
            return new Response<>(Response.FAIL, "issue受托者为空", null);
        }
        if (!issue.getStatus().equals("open") ) {
            return new Response<>(Response.FAIL, "issue状态不为开发中", null);
        }
        issueManager.createIssue(issue);
        return new Response<>(Response.SUCCESS, "创建issue成功", issue);
    }

    public Response<Issue> edit(Issue issue) {
        if (issue == null) {
            return new Response<>(Response.FAIL, "issue为空", null);
        }
        if (issueManager.findIssueById(issue.getId()) == null) {
            return new Response<>(Response.FAIL, "issue不存在", null);
        }
        if (projectManager.findProjectById(issue.getProjectId()) == null) {
            return new Response<>(Response.FAIL, "新issue所属项目为空", null);
        }
        if (userManager.findUserById(issue.getReviewerId()) == null) {
            return new Response<>(Response.FAIL, "新issue指派者为空", null);
        }
        if (userManager.findUserById(issue.getAssigneeId()) == null) {
            return new Response<>(Response.FAIL, "新issue受托者为空", null);
        }
        issueManager.editIssue(issue);
        return new Response<>(Response.SUCCESS, "修改issue成功", issue);
    }

    public Response<List<Issue>> listByProject(Integer projectId) {
        if (projectManager.findProjectById(projectId) == null) {
            return new Response<>(Response.FAIL, "项目不存在", null);
        }
        List<Issue> issues = issueManager.findIssuesByProjectId(projectId);
        return new Response<>(Response.SUCCESS, "获取项目下所有issue成功", issues);
    }

    public Response<List<Issue>> listByUser(Integer userId) {
        if (userManager.findUserById(userId) == null) {
            return new Response<>(Response.FAIL, "用户不存在", null);
        }
        List<Issue> issues = issueManager.findIssuesByAssigneeId(userId);
        return new Response<>(Response.SUCCESS, "获取用户下所有issue成功", issues);
    }
}
