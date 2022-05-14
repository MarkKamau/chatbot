package com.chatbot.chatbot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequest {
    @Enumerated(EnumType.STRING)
    private ChatSender sender;
    private Long senderId;
    private String chatMessage;
}
