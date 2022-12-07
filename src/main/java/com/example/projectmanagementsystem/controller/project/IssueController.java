package com.example.projectmanagementsystem.controller.project;

import com.example.projectmanagementsystem.model.entity.Issue;
import com.example.projectmanagementsystem.service.project.IssueService;
import com.example.projectmanagementsystem.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("IssueController")
@RequestMapping("/project/issue")
public class IssueController {
    private final IssueService issueService;
    @Autowired
    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }
    @GetMapping("/list/{project_id}")
    public Response<List<Issue>> getIssueList(@PathVariable Integer project_id) {
        return issueService.listByProject(project_id);
    }
    @PostMapping("/create")
    public Response<Issue> createIssue(@RequestBody Issue issue) {
        return issueService.create(issue);
    }
    @PostMapping("/edit")
    public Response<Issue> editIssueStatus(@RequestBody Issue issue) {
        return issueService.edit(issue);
    }
}
