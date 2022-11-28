package com.example.projectmanagementsystem.controller.project;

import com.example.projectmanagementsystem.service.project.MemberService;
import com.example.projectmanagementsystem.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("MemberController")
@RequestMapping("/project/member")
public class MemberController {
    private final MemberService memberService;

    @Autowired
    MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/delete")
    public Response<String> quit(@RequestParam Integer project_id,
                                 @RequestParam Integer user_id) {
        return memberService.quit(project_id, user_id);
    }
    @GetMapping("/role") // 找不到Response.code = 2
    public Response<String> getRole(@RequestParam Integer project_id,
                                    @RequestParam Integer user_id) {
        return memberService.getRole(project_id, user_id);
    }
    @PostMapping("/role")// 更改角色（除所有者）没改权限code = 4
    public Response<String> editRole(@RequestParam Integer project_id,
                                     @RequestParam Integer user_id,
                                     @RequestParam String role) {
        return memberService.editRole(project_id, user_id, role);
    }
}
