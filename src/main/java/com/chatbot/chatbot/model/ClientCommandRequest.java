package com.chatbot.chatbot.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientCommandRequest {

    private Long id;

    private Long employee;

    private Long client;

    private Long waitTime;

    private String messageType;

    private Long template;
}
