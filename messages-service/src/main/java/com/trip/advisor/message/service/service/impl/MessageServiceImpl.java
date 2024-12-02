package com.trip.advisor.message.service.service.impl;

import com.trip.advisor.message.service.model.dto.EmailDTO;
import com.trip.advisor.message.service.service.MessageService;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {


    @Override
    public void sendEmail(EmailDTO emailDTO) {

    }

    @Override
    public void saveEmail(EmailDTO emailDTO) {
    //TODO create db and save emails
    }
}
