package com.chatbot.chatbot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {
    private ChatSender sender;
    private LocalDateTime dateCreate;
    private String Chat;
}
