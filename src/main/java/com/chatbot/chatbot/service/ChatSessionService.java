package com.chatbot.chatbot.service;

import com.chatbot.chatbot.entity.ChatSession;

import java.util.List;


public interface ChatSessionService {

    void createSession(Long clientId) throws Exception;

    void deleteSession(Long clientId) throws Exception;

    long timeInSession(Long clientId) throws Exception;

    List<ChatSession> getChatSessionsWithActiveClientCommands();

    List<ChatSession> getChatSessions();
}
