package com.example.projectmanagementsystem.controller.project;

import com.example.projectmanagementsystem.service.project.MemberService;
import com.example.projectmanagementsystem.service.project.ProjectService;
import com.example.projectmanagementsystem.util.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("MemberController")
@RequestMapping("/project/member")
public class MemberController {
    private final ProjectService projectService;
    private final MemberService memberService;

    public MemberController(ProjectService projectService, MemberService memberService) {
        this.projectService = projectService;
        this.memberService = memberService;
    }

    @PostMapping("/invite")
    public Response<String> inviteMember(@RequestParam Integer owner_id,
                                         @RequestParam Integer new_member_id,
                                         @RequestParam Integer project_id
    ) {
        return memberService.inviteMember(owner_id, project_id, new_member_id);
    }
}
