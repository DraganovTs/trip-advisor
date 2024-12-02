package com.trip.advisor.message.service.service.impl;

import com.trip.advisor.message.service.model.dto.EmailDTO;
import com.trip.advisor.message.service.service.MessageService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    private final JavaMailSender mailSender;

    public MessageServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(EmailDTO emailDTO) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("");
        simpleMailMessage.setTo(emailDTO.getTo());
        simpleMailMessage.setSubject(emailDTO.getSubject());
        simpleMailMessage.setText(emailDTO.getMessage());

        mailSender.send(simpleMailMessage);
    }

    @Override
    public void saveEmail(EmailDTO emailDTO) {
    //TODO create db and save emails
    }
}
