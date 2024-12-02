package com.trip.advisor.message.service.controller;

import com.trip.advisor.message.service.model.dto.EmailDTO;
import com.trip.advisor.message.service.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mails")
public class EmailController {

    private final MessageService messageService;

    public EmailController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/send-email")
    public ResponseEntity sendEmail(@RequestBody EmailDTO emailDTO) {
        messageService.sendEmail(emailDTO);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Email sent");
    }
}
