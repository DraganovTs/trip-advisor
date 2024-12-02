package com.trip.advisor.message.service.service;

import com.trip.advisor.message.service.model.dto.EmailDTO;

public interface MessageService {

    void sendEmail(EmailDTO emailDTO);

    void saveEmail(EmailDTO emailDTO);

}
