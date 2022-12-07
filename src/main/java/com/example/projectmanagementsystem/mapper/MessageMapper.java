package com.example.projectmanagementsystem.mapper;

import com.example.projectmanagementsystem.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageMapper extends JpaRepository<Message, Integer> {
    Message findMessageById(Integer id);
    List<Message> findAllByUserId(Integer userId);
    List<Message> findAllByUserIdAndSeenIsFalse(Integer userId);
    void deleteById(Integer id);
}
