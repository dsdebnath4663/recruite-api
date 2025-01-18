package com.recruitment.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private FileService fileService;


    public EmailService ( JavaMailSender mailSender ) {
        this.mailSender = mailSender;
    }

    public void sendHtmlMail ( String to , String subject , String htmlBody ) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message , true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody , true); // Enable HTML content
        helper.setFrom(fromEmail);

        mailSender.send(message);
    }

    public void sendAccountCreatedEmail ( String to , String userName , String userId , String password , String loginUrl ) throws Exception {
        String emailTemplate = fileService.getFileContent("default-email-template.html"); // Load default-email-template file content
        String htmlBody = emailTemplate.formatted(userName , userId , password , loginUrl , LocalDate.now().getYear());
        sendHtmlMail(to , "Your Recruit Account is Ready!" , htmlBody);
    }
}
