package com.example.projectmanagementsystem.controller.user;

import com.example.projectmanagementsystem.model.entity.Message;
import com.example.projectmanagementsystem.service.user.MessageService;
import com.example.projectmanagementsystem.util.Response;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("UserMessageController")
@RequestMapping("/user/message")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/list/unseen")
    public Response<List<Message>> listUnseenMessagesByUser(@RequestParam("user_id") Integer userId) {
        return messageService.listUnseenByUser(userId);
    }
    @GetMapping("/list")
    public Response<List<Message>> listMessagesByUser(@RequestParam("user_id") Integer userId) {
        return messageService.listByUser(userId);
    }
    @PostMapping("/see")
    public Response<String> seeMessage(@RequestParam("message_id") Integer messageId) {
        return messageService.seeMessage(messageId);
    }
}
