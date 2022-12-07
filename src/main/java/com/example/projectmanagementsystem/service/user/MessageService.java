package com.example.projectmanagementsystem.service.user;

import com.example.projectmanagementsystem.manager.user.MessageManager;
import com.example.projectmanagementsystem.manager.user.UserManager;
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
    @Autowired
    MessageService(UserManager userManager, MessageManager messageManager) {
        this.userManager = userManager;
        this.messageManager = messageManager;
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
