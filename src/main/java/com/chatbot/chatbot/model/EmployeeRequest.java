package com.chatbot.chatbot.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class EmployeeRequest {

    private String firstName;

    private String lastName;
}
