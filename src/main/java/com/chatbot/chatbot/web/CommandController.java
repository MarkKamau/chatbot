package com.chatbot.chatbot.web;


import com.chatbot.chatbot.model.CommandRequest;
import com.chatbot.chatbot.service.CommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("command")
@RequiredArgsConstructor
public class CommandController {

    final CommandService commandService;

    @PostMapping("create")
    void create(@RequestBody CommandRequest commandRequest) throws Exception {
        commandService.save(commandRequest);
    }
}
