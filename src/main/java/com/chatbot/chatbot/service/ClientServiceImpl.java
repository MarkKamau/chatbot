package com.chatbot.chatbot.service;

import com.chatbot.chatbot.entity.Client;
import com.chatbot.chatbot.model.ClientRequest;
import com.chatbot.chatbot.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService{

    final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }


    public Optional<Client> getClient(Long clientId){
        return clientRepository.findById(clientId);
    }

    public Client save(ClientRequest clientRequest){
        return clientRepository.save(new Client(clientRequest.getCustomerNumber(), clientRequest.getFirstName(), clientRequest.getLastName()));
    }

    public Optional<Client> findClientById(Long clientId){
        return clientRepository.findById(clientId);
    }
}
