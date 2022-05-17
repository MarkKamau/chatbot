package com.chatbot.chatbot.web;

import com.chatbot.chatbot.service.ChatSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("chatSession")
@RequiredArgsConstructor
public class ChatSessionController {

    final ChatSessionService chatSessionService;

    @PostMapping("client/{clientId}/create")
    public void createChatSession(@PathVariable Long clientId) throws Exception {
        chatSessionService.createSession(clientId);
    }

    @DeleteMapping("client/{clientId}/delete")
    public void deleteChatSession(@PathVariable Long clientId) throws Exception {
        chatSessionService.deleteSession(clientId);
    }
}
