package com.example.projectmanagementsystem.service.user;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.SneakyThrows;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;

@Service("UserMailService")
/*
  @author M.C
 * @description EmailServiceImpl
 * @date 2019-02-20 9:01
 */
public class MailServiceImpl implements MailService {

    @Resource
    private JavaMailSender javaMailSender;

    @Override
    @SneakyThrows
    public void sendMail(String receivers, String subject, String htmlMsg){
        MimeMessage mail = javaMailSender.createMimeMessage();
        mail.addRecipients(Message.RecipientType.TO, InternetAddress.parse(receivers));
        mail.setFrom(new InternetAddress("mail-bot@zephyr-zdz.space"));
        mail.setSentDate(new Date());
        mail.setSubject(subject);
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(htmlMsg, "text/html;charset=utf-8");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        mail.setContent(multipart);
        javaMailSender.send(mail);
    }
}
