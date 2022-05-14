package com.chatbot.chatbot.service;

import com.chatbot.chatbot.entity.Client;
import com.chatbot.chatbot.model.ClientRequest;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<Client> getAllClients();

    Optional<Client> getClient(Long clientId);

    Client save(ClientRequest clientRequest);

    Optional<Client> findClientById(Long clientId);
}
