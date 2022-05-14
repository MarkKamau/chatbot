package com.chatbot.chatbot.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class TemplateRequest {

    private Long id;

    private String name;

    private String value;
}
