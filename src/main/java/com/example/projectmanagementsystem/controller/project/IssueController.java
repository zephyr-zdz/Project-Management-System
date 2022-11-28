package com.example.projectmanagementsystem.controller.project;

import com.example.projectmanagementsystem.model.entity.Issue;
import com.example.projectmanagementsystem.service.project.IssueService;
import com.example.projectmanagementsystem.service.project.MemberService;
import com.example.projectmanagementsystem.service.project.ProjectService;
import com.example.projectmanagementsystem.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("IssueController")
@RequestMapping("/project/issue")
public class IssueController {
    private final IssueService issueService;
    private final ProjectService projectService;
    private final MemberService memberService;

    @Autowired
    public IssueController(IssueService issueService, ProjectService projectService, MemberService memberService) {
        this.issueService = issueService;
        this.projectService = projectService;
        this.memberService = memberService;
    }
    @PostMapping("/create")
    public Response<Issue> createIssue(@RequestBody Issue issue) {
        return issueService.create(issue);
    }

    @PostMapping("/edit")
public Response<Issue> editIssueStatus(@RequestBody Issue issue) {
        return issueService.edit(issue);
    }

    @GetMapping("/list")
    public Response<List<Issue>> getIssueList(@RequestParam("project_id") Integer projectId) {
        return issueService.listByProject(projectId);
    }
    @GetMapping("/list-by-user")
    public Response<List<Issue>> getProjectListByUser(@RequestParam("user_id") Integer userId) {
        return issueService.listByUser(userId);
    }
}
