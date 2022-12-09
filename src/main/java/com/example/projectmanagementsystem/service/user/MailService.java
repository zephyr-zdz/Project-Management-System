package com.example.projectmanagementsystem.service.user;

public interface MailService {
    void sendMail(String receivers, String subject, String htmlMsg);
}
