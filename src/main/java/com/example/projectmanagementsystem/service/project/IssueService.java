package com.example.projectmanagementsystem.service.project;

import com.example.projectmanagementsystem.manager.project.IssueManager;
import com.example.projectmanagementsystem.manager.project.ProjectManager;
import com.example.projectmanagementsystem.manager.user.UserManager;
import com.example.projectmanagementsystem.model.ClassAdapter;
import com.example.projectmanagementsystem.model.entity.Issue;
import com.example.projectmanagementsystem.model.vo.IssueVO;
import com.example.projectmanagementsystem.service.user.MessageService;
import com.example.projectmanagementsystem.util.Response;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Transactional
@Service("ProjectIssueService")
public class IssueService {
    private final IssueManager issueManager;
    private final ProjectManager projectManager;
    private final UserManager userManager;
    private final MessageService messageService;
    private final ClassAdapter classAdapter;

    public IssueService(IssueManager issueManager, ProjectManager projectManager, UserManager userManager, MessageService messageService, ClassAdapter classAdapter
                        ) {
        this.issueManager = issueManager;
        this.projectManager = projectManager;
        this.userManager = userManager;
        this.messageService = messageService;
        this.classAdapter = classAdapter;
    }
    public Response<Issue> create(Issue issue) {
        if (issue == null) {
            return new Response<>(Response.FAIL, "issue为空", null);
        }
        if (projectManager.findProjectById(issue.getProjectId()) == null) {
            return new Response<>(Response.FAIL, "issue所属项目为空", null);
        }
        if (Objects.equals(issue.getAssigneeId(), issue.getReviewerId())) {
            return new Response<>(Response.FAIL, "issue的assignee和reviewer不能相同", null);
        }
        if (userManager.findUserById(issue.getReviewerId()) == null) {
            return new Response<>(Response.FAIL, "issue指派者为空", null);
        }
        if (userManager.findUserById(issue.getAssigneeId()) == null) {
            return new Response<>(Response.FAIL, "issue受托者为空", null);
        }
        if (!(issue.getStatus() == null) && !(issue.getStatus().equals("open"))) {
            return new Response<>(Response.FAIL, "issue状态不为开发中", null);
        }
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        issue.setDate(ft.format(dNow));
        issueManager.createIssue(issue);
        messageService.createMessage(issue.getId());
        return new Response<>(Response.SUCCESS, "创建issue成功", issue);
    }

    public Response<Issue> edit(Issue issue) {
        if (issue == null) {
            return new Response<>(Response.FAIL, "issue为空", null);
        }
        if (issueManager.findIssueById(issue.getId()) == null) {
            return new Response<>(Response.FAIL, "issue不存在", null);
        }
        if (issue.getStatus() == null) {
            return new Response<>(Response.FAIL, "issue不存在", null);
        }
        Issue oldIssue = issueManager.findIssueById(issue.getId());
        issue.setProjectId(oldIssue.getProjectId());
        issue.setReviewerId(oldIssue.getReviewerId());
        issue.setAssigneeId(oldIssue.getAssigneeId());
        issue.setTitle(oldIssue.getTitle());
        issue.setDescription(oldIssue.getDescription());
        issue.setDate(oldIssue.getDate());
        issueManager.editIssue(issue);
        return new Response<>(Response.SUCCESS, "修改issue成功", issue);
    }

    public Response<List<IssueVO>> listByProject(Integer projectId) {
        if (projectManager.findProjectById(projectId) == null) {
            return new Response<>(Response.FAIL, "项目不存在", null);
        }
        List<Issue> issues = issueManager.findIssuesByProjectId(projectId);
        List<IssueVO> issueVOs = issues.stream().map(classAdapter::fromIssue2IssueVO).toList();
        return new Response<>(Response.SUCCESS, "获取项目下所有issue成功", issueVOs);
    }
    public Response<List<IssueVO>> listAssigningByUser(Integer userId) {
        if (userManager.findUserById(userId) == null) {
            return new Response<>(Response.FAIL, "用户不存在", null);
        }
        List<Issue> issues = issueManager.findIssuesByReviewerId(userId);
        List<IssueVO> issueVOs = issues.stream().map(classAdapter::fromIssue2IssueVO).toList();
        return new Response<>(Response.SUCCESS, "获取用户指派他人的所有issue成功", issueVOs);
    }

    public Response<List<IssueVO>> listAssignedByUser(Integer userId) {
        if (userManager.findUserById(userId) == null) {
            return new Response<>(Response.FAIL, "用户不存在", null);
        }
        List<Issue> issues = issueManager.findIssuesByAssigneeId(userId);
        List<IssueVO> issueVOs = issues.stream().map(classAdapter::fromIssue2IssueVO).toList();
        return new Response<>(Response.SUCCESS, "获取用户被指派的所有issue成功", issueVOs);
    }
}
