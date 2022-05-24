package com.chatbot.chatbot.service;

import com.chatbot.chatbot.entity.ChatSession;
import com.chatbot.chatbot.entity.Client;
import com.chatbot.chatbot.entity.Track;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface ChatSessionService {

    void createSession(Long clientId) throws Exception;

    void createSession(Long clientId, Track track) throws Exception;

    void deleteSession(Long clientId) throws Exception;

    long timeInSession(Long clientId) throws Exception;

    List<ChatSession> getChatSessionsWithActiveClientCommands();

    List<ChatSession> getChatSessions();

    void updateSessionTrack(Long clientId, LocalDateTime sessionStartTime, Track track) throws Exception;

    Optional<ChatSession> findCurrentClientChatSession(Client client);
}
