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
    @GetMapping("/list/assigning/{user_id}")
    public Response<List<Issue>> getAssigningListByUser(@PathVariable Integer user_id) {
        return issueService.listAssigningByUser(user_id);
    }
    @GetMapping("/list/assigned/{user_id}")
    public Response<List<Issue>> getAssignedListByUser(@PathVariable Integer user_id) {
        return issueService.listAssignedByUser(user_id);
    }
}
