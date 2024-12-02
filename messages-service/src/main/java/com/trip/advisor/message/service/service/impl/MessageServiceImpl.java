package com.trip.advisor.message.service.service.impl;

import com.trip.advisor.message.service.model.dto.EmailDTO;
import com.trip.advisor.message.service.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
    @Autowired
    private final JavaMailSender mailSender;

    public MessageServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(EmailDTO emailDTO) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("model3dservice@gmail.com");
        simpleMailMessage.setTo(emailDTO.getTo());
        simpleMailMessage.setSubject(emailDTO.getSubject());
        simpleMailMessage.setText(emailDTO.getMessage());

        try {
            mailSender.send(simpleMailMessage);
        } catch (MailException e) {
            logger.error(e.getLocalizedMessage(),e);
        }
    }

    @Override
    public void saveEmail(EmailDTO emailDTO) {
    //TODO create db and save emails
    }
}
