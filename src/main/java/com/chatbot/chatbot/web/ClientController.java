package com.chatbot.chatbot.web;


import com.chatbot.chatbot.entity.Client;
import com.chatbot.chatbot.model.ClientRequest;
import com.chatbot.chatbot.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("client")
@RequiredArgsConstructor
public class ClientController {

    final ClientService clientService;

    @PostMapping("create")
    public void create(@RequestBody ClientRequest clientRequest){
        clientService.save(clientRequest);
    }

    @GetMapping
    public List<Client> getAll(){
        return clientService.getAllClients();
    }

}
