package com.chatbot.chatbot.web;

import com.chatbot.chatbot.model.ChatRequest;
import com.chatbot.chatbot.model.ChatResponse;
import com.chatbot.chatbot.service.ChatService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping()
    public void ClientChat(@RequestBody ChatRequest chatRequest){
        chatService.makeChatRequest(chatRequest);
    }

    @GetMapping("client/{clientId}")
    public List<ChatResponse> getClientChat(@PathVariable("clientId") Long clientId){
        return chatService.getClientChat(clientId);
    }
}
