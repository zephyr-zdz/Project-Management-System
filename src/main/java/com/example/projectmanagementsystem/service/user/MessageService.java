package com.example.projectmanagementsystem.service.user;

import com.example.projectmanagementsystem.manager.project.IssueManager;
import com.example.projectmanagementsystem.manager.user.MessageManager;
import com.example.projectmanagementsystem.manager.user.UserManager;
import com.example.projectmanagementsystem.model.entity.Issue;
import com.example.projectmanagementsystem.model.entity.Message;
import com.example.projectmanagementsystem.model.entity.User;
import com.example.projectmanagementsystem.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("UserMessageService")
public class MessageService {
    private final UserManager userManager;
    private final MessageManager messageManager;
    private final IssueManager issueManager;
    private final MailServiceImpl mailService;
    @Autowired
    MessageService(UserManager userManager, MessageManager messageManager, IssueManager issueManager, MailServiceImpl mailService) {
        this.userManager = userManager;
        this.messageManager = messageManager;
        this.issueManager = issueManager;
        this.mailService = mailService;
    }
    public void createMessage(Integer issueId) {
        Issue issue = issueManager.findIssueById(issueId);
        User user = userManager.findUserById(issue.getAssigneeId());
        Message message = new Message();
        message.initialize(user.getId(), issueId);
        messageManager.save(message);
        String mailReceiver = user.getEmail();
        String mailSubject = "你有一个新任务：" + issue.getTitle();
        String mailContent = "任务指派人为：" + userManager.findUserById(issue.getReviewerId()).getUsername() + "\n"
                           + "任务内容为：" + issue.getDescription();
        mailService.sendMail(mailReceiver, mailSubject, mailContent);
    }
    public Response<List<Message>> listUnseenByUser(Integer userId) {
        User user = userManager.findUserById(userId);
        if (user == null) {
            return new Response<>(Response.FAIL, "用户不存在", null);
        }
        return new Response<>(Response.SUCCESS, "获取用户消息成功", messageManager.findUnseenMessagesByUser(user));
    }
    public Response<List<Message>> listByUser(Integer userId) {
        User user = userManager.findUserById(userId);
        if (user == null) {
            return new Response<>(Response.FAIL, "用户不存在", null);
        }
        return new Response<>(Response.SUCCESS, "获取用户消息成功", messageManager.findMessagesByUser(user));
    }
    public Response<String> seeMessage(Integer messageId) {
        Message message = messageManager.findMessageById(messageId);
        if (message == null) {
            return new Response<>(Response.FAIL, "消息不存在", null);
        }
        message.setSeen(true);
        messageManager.save(message);
        return new Response<>(Response.SUCCESS, "消息已读", null);
    }
}
