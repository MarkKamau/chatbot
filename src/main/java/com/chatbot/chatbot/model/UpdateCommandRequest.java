package com.chatbot.chatbot.model;

import com.chatbot.chatbot.entity.Client;
import com.chatbot.chatbot.entity.Employee;
import com.chatbot.chatbot.entity.Template;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCommandRequest {

    private Long id;

    private Employee employee;

    private Client client;

    private Long waitTime;

    private String messageType;

    private Template template;
}
