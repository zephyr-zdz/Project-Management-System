package com.example.projectmanagementsystem.controller.user;

import com.example.projectmanagementsystem.model.entity.Issue;
import com.example.projectmanagementsystem.service.project.IssueService;
import com.example.projectmanagementsystem.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("UserIssueController")
@RequestMapping("/user/issue")
public class IssueController {
    private final IssueService issueService;
    @Autowired
    public IssueController(IssueService issueService) {
        this.issueService = issueService;

    }
    @GetMapping("/list/assigning")
    public Response<List<Issue>> getAssigningListByUser(@RequestParam("user_id") Integer userId) {
        return issueService.listAssigningByUser(userId);
    }
    @GetMapping("/list/assigned")
    public Response<List<Issue>> getAssignedListByUser(@RequestParam("user_id") Integer userId) {
        return issueService.listAssignedByUser(userId);
    }
}
