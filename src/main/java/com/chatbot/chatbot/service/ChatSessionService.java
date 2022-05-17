package com.chatbot.chatbot.service;

import org.springframework.stereotype.Service;


public interface ChatSessionService {

    void createSession(Long clientId) throws Exception;

    void deleteSession(Long clientId) throws Exception;

    long timeInSession(Long clientId) throws Exception;


}
