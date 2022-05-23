package com.chatbot.chatbot.web;


import com.chatbot.chatbot.entity.DefaultCommand;
import com.chatbot.chatbot.model.ClientCommandRequest;
import com.chatbot.chatbot.model.DefaultCommandRequest;
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


    @PostMapping("client/create")
    void createClientCommand(@RequestBody ClientCommandRequest clientCommandRequest) throws Exception {
        commandService.createClientCommand(clientCommandRequest);
    }

    @PostMapping("default/create")
    void createDefaultCommand(@RequestBody DefaultCommandRequest defaultCommandRequest) throws Exception {
        commandService.createDefaultCommand(defaultCommandRequest);
    }
}
