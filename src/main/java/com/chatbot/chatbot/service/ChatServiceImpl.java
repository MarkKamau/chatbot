package com.chatbot.chatbot.service;

import com.chatbot.chatbot.entity.Chat;
import com.chatbot.chatbot.entity.Command;
import com.chatbot.chatbot.entity.Track;
import com.chatbot.chatbot.model.ChatRequest;
import com.chatbot.chatbot.model.ChatResponse;
import com.chatbot.chatbot.model.ChatSender;
import com.chatbot.chatbot.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{

    final ChatRepository chatRepository;

    final ClientService clientService;

    final TrackService trackService;

    final ChatSessionService chatSessionService;


/*    public ChatServiceImpl(ChatRepository chatRepository, ClientService clientService) {
        this.chatRepository = chatRepository;
        this.clientService = clientService;
    }*/

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

        if (chatRequest.getSender().equals(ChatSender.CLIENT)){
            processClientChat(chatRequest);
        } else if (chatRequest.getSender().equals(ChatSender.EMPLOYEE)) {
            createEmployeeChat();
        } else {
            createBotChat();
        }
    }

    private Optional<Track> findTargetTrack(String chat){
        if (trackService.getAllTracks().isPresent()) {
            List<Track> tracks = trackService.getAllTracks().get();
            for (Track track : tracks) {
                if (track.getKeyWords() !=null ) {
                    String[]  strings=chat.split(" ");
                    for (String word:chat.split(" ")) {
                        if (track.getKeyWords().toLowerCase().contains(word.toLowerCase())) {
                            return Optional.of(track);
                        }
                    }

                }
            }
        }

        return Optional.empty();
/*        List<Track> tracks= trackService.getAllTracks()
                .stream().filter(tracks1 -> !tracks1.stream().filter(track1 ->
                    !Arrays.stream(chat.split(" ")).filter( s -> track1.getKeyWords().contains(s)).collect(Collectors.toList()).isEmpty()
                ).collect(Collectors.toList()).isEmpty()).collect(Collectors.toList());*/
    }


    private void processClientChat(ChatRequest chatRequest){
        clientService.getClient(chatRequest.getSenderId())
                .ifPresent(client ->
                    findLastBotToClientChat(client.getId())
                            .ifPresent(command -> {
                                saveChat(new Chat(client,command,chatRequest.getChatMessage(),ChatSender.CLIENT, LocalDateTime.now()));

                                findTargetTrack(chatRequest.getChatMessage()).ifPresent( track ->
                                {
                                    try {

                                        chatSessionService.updateSessionTrack(chatRequest.getSenderId(),LocalDateTime.now(), track);
                                    } catch (Exception e) {
                                       // throw new RuntimeException(e);
                                    }
                            });
                        }));
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
