package com.chatbot.chatbot.service;

import com.chatbot.chatbot.entity.Client;
import com.chatbot.chatbot.entity.Command;
import com.chatbot.chatbot.entity.DefaultCommand;
import com.chatbot.chatbot.model.ClientCommandRequest;
import com.chatbot.chatbot.model.DefaultCommandRequest;

import java.util.List;

public interface CommandService {

  void initiateCommand();

  //void processCommand();

  List<Command> getDueClientCommands();

  void processDefaultChats(List<DefaultCommand> dueDefaultCommands, Client client);

  void createClientCommand(ClientCommandRequest commandRequest) throws Exception;

  void createDefaultCommand(DefaultCommandRequest defaultCommandRequest) throws Exception;

  void update(ClientCommandRequest commandRequest);

}
