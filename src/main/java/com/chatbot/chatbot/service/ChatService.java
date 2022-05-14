package com.chatbot.chatbot.service;

import com.chatbot.chatbot.entity.Chat;
import com.chatbot.chatbot.entity.Command;
import com.chatbot.chatbot.model.ChatRequest;
import com.chatbot.chatbot.model.ChatResponse;

import java.util.List;
import java.util.Optional;

public interface ChatService {

    List<ChatResponse> getClientChat(Long clientId);

    void makeChatRequest(ChatRequest chatRequest);

    public void saveChats(List<Chat> chats);

    public void saveChat(Chat chat);

    Optional<Command> findLastBotToClientChat(Long clientId);
}
