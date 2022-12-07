package com.example.projectmanagementsystem.manager.user;

import com.example.projectmanagementsystem.mapper.MessageMapper;
import com.example.projectmanagementsystem.model.entity.Message;
import com.example.projectmanagementsystem.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("UserMessageManager")
public class MessageManager {
    private final MessageMapper messageMapper;

    @Autowired
    public MessageManager(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    public void save(Message message) {
        messageMapper.save(message);
    }
    public List<Message> findUnseenMessagesByUser(User user) {
        return messageMapper.findAllByUserIdAndSeenIsFalse(user.getId());
    }
    public Message findMessageById(Integer messageId) {
        return messageMapper.findMessageById(messageId);
    }

    public List<Message> findMessagesByUser(User user) {
        return messageMapper.findAllByUserId(user.getId());
    }
}
