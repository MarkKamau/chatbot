package com.chatbot.chatbot.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultCommandRequest {

    private Long id;

    private Long employee;

    private Long waitTime;

    private String messageType;

    private Long template;

    private Long track;
}
