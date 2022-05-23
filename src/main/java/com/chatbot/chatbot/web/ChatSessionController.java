package com.chatbot.chatbot.web;

import com.chatbot.chatbot.service.ChatSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("chatSession")
@RequiredArgsConstructor
public class ChatSessionController {

    final ChatSessionService chatSessionService;
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/create")
    public void createChatSession(@RequestParam Long clientId) throws Exception {
        chatSessionService.createSession(clientId);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("client/{clientId}/delete")
    public void deleteChatSession(@PathVariable Long clientId) throws Exception {
        chatSessionService.deleteSession(clientId);
    }
}
