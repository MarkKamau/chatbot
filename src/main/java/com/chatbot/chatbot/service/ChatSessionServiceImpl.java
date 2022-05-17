package com.chatbot.chatbot.service;

import com.chatbot.chatbot.entity.ChatSession;
import com.chatbot.chatbot.entity.Client;
import com.chatbot.chatbot.repository.ChatSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatSessionServiceImpl implements  ChatSessionService{

    final ChatSessionRepository chatSessionRepository;

    final ClientService clientService;

    @Override
    public void createSession(Long clientId) throws Exception {
        Client client=clientService.findClientById(clientId).orElseThrow(() -> new Exception("Client nto found"));
        chatSessionRepository.save(new ChatSession(client, LocalDateTime.now()));
    }

    @Override
    public void deleteSession(Long clientId) throws Exception {
        Client client=clientService.findClientById(clientId).orElseThrow(() -> new Exception("Client nto found"));
        ChatSession chatSession= chatSessionRepository.findChatSessionByClient(client).orElseThrow(() -> new Exception("Client has no active session"));
        chatSessionRepository.delete(chatSession);
    }

    @Override
    public long timeInSession(Long clientId) throws Exception {
        Client client=clientService.findClientById(clientId).orElseThrow(() -> new Exception("Client nto found"));
        ChatSession chatSession= chatSessionRepository.findChatSessionByClient(client).orElseThrow(() -> new Exception("Client has no active session"));
        return Duration.between(chatSession.getDateStarted(), LocalDateTime.now()).toMinutes();
    }
}
