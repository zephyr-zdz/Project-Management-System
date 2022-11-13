package com.example.projectmanagementsystem.controller.project;

import com.example.projectmanagementsystem.service.project.MemberService;
import com.example.projectmanagementsystem.util.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("MemberController")
@RequestMapping("/project/member")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/delete")
    public Response<String> quit(@RequestParam Integer project_id,
                                 @RequestParam Integer user_id) {
        return memberService.quit(project_id, user_id);
    }
}
