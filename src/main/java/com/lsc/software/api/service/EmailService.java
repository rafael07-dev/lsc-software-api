package com.lsc.software.api.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendConfirmationEmail(String to, String token){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Confirmation");
        message.setText("Para confirmar su cuenta, haga click en el siguiente link para el usuario: \n" +
                "http://localhost:8080/api/auth/confirmation?token=" + token);
        mailSender.send(message);
    }
}
