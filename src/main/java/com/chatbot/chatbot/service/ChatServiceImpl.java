package com.chatbot.chatbot.service;

import com.chatbot.chatbot.entity.Chat;
import com.chatbot.chatbot.entity.Command;
import com.chatbot.chatbot.model.ChatRequest;
import com.chatbot.chatbot.model.ChatResponse;
import com.chatbot.chatbot.model.ChatSender;
import com.chatbot.chatbot.repository.ChatRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ChatServiceImpl implements ChatService{

    final ChatRepository chatRepository;

    final ClientService clientService;

    public ChatServiceImpl(ChatRepository chatRepository, ClientService clientService) {
        this.chatRepository = chatRepository;
        this.clientService = clientService;
    }

    @Override
    public List<ChatResponse> getClientChat(Long clientId) {
        Optional<List<Chat>> optionalChats = chatRepository.findChatByClient(clientId);

        return optionalChats.isPresent()? getChatResponse(optionalChats.get()): Collections.emptyList();
    }

    private List<ChatResponse> getChatResponse(List<Chat> chats){
        List<ChatResponse> chatResponses = new ArrayList<>();
        for (Chat chat: chats) {
            chatResponses.add(new ChatResponse(chat.getSender(),chat.getDateCreated(),chat.getMessage()));
        }
        return chatResponses;
    }

    @Override
    public void makeChatRequest(ChatRequest chatRequest) {
        //TODO create chat logic
        if (chatRequest.getSender().equals(ChatSender.CLIENT)){
            processClientChat(chatRequest);
        } else if (chatRequest.getSender().equals(ChatSender.EMPLOYEE)) {
            createEmployeeChat();
        } else {
            createBotChat();
        }
    }


    private void processClientChat(ChatRequest chatRequest){
        clientService.getClient(chatRequest.getSenderId())
                .ifPresent(client ->
                    findLastBotToClientChat(client.getId())
                            .ifPresent(command ->
                                    saveChat(new Chat(command,chatRequest.getChatMessage(),ChatSender.CLIENT, LocalDateTime.now()))));
    }

    public Optional<Command> findLastBotToClientChat(Long clientId){
        return chatRepository.findChatByClient(clientId)
                .orElse(Collections.emptyList())
                    .stream()
                        .filter(chat -> chat.getSender().equals(ChatSender.BOT))
                            .max(Comparator.comparing(Chat::getId))
                                .map(Chat::getCommand);
    }

    public void saveChat(Chat chat){
        chatRepository.save(chat);
    }

    public void saveChats(List<Chat> chats){
        chatRepository.saveAll(chats);
    }

    private void createEmployeeChat(){
        //TODO Implement
    }

    private void createBotChat(){
        //TODO Implement
    }
}
