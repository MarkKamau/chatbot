package com.chatbot.chatbot.service;

import com.chatbot.chatbot.entity.ChatSession;
import com.chatbot.chatbot.entity.Client;
import com.chatbot.chatbot.entity.Track;
import com.chatbot.chatbot.repository.ChatSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatSessionServiceImpl implements  ChatSessionService{

    final ChatSessionRepository chatSessionRepository;

    final TrackService trackService;

    final ClientService clientService;

    @Override
    public void createSession(Long clientId) throws Exception {
        Client client=clientService.findClientById(clientId).orElseThrow(() -> new Exception("Client nto found"));
        Track track= trackService.getStartTrack();
        deleteSession(clientId);
        chatSessionRepository.save(new ChatSession(client, LocalDateTime.now(),track));
    }

    @Override
    public void createSession(Long clientId, Track track) throws Exception {

        Client client=clientService.findClientById(clientId).orElseThrow(() -> new Exception("Client nto found"));
        deleteSession(clientId);
        chatSessionRepository.save(new ChatSession(client, LocalDateTime.now(),track));
    }

    @Override
    public void deleteSession(Long clientId) throws Exception {
        Client client=clientService.findClientById(clientId).orElseThrow(() -> new Exception("Client nto found"));

        chatSessionRepository.findChatSessionByClient(client).ifPresent(chatSession ->  chatSessionRepository.delete(chatSession));
    }

    @Override
    public void updateSessionTrack(Long clientId, LocalDateTime sessionStartTime, Track track) throws Exception {
        Client client=clientService.findClientById(clientId).orElseThrow(() -> new Exception("Client nto found"));
        ChatSession chatSession= chatSessionRepository.findChatSessionByClient(client).orElseThrow(() -> new Exception("Client has no active session"));
        chatSession.setDateStarted(sessionStartTime);
        chatSession.setCurrentTrack(track);
        chatSessionRepository.save(chatSession);
    }

    @Override
    public Optional<ChatSession> findCurrentClientChatSession(Client client) {

        return chatSessionRepository.findChatSessionByClient(client);
    }

    @Override
    public long timeInSession(Long clientId) throws Exception {
        Client client=clientService.findClientById(clientId).orElseThrow(() -> new Exception("Client nto found"));
        ChatSession chatSession= chatSessionRepository.findChatSessionByClient(client).orElseThrow(() -> new Exception("Client has no active session"));
        return chatSession.getTimeInSession();
    }

    public List<ChatSession> getChatSessionsWithActiveClientCommands(){
        List<ChatSession> chatSessions= chatSessionRepository.
                findAll()
                .stream()
                .filter(chatSession -> chatSession.getCurrentClientCommand().isPresent())
                .collect(Collectors.toList());

        return chatSessions;
    }

    public List<ChatSession> getChatSessions(){
       return  chatSessionRepository.
                findAll();
    }


}
