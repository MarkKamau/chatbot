package com.chatbot.chatbot.service;

import com.chatbot.chatbot.entity.Client;
import com.chatbot.chatbot.entity.Command;
import com.chatbot.chatbot.entity.DefaultCommand;
import com.chatbot.chatbot.model.CommandRequest;

import java.util.List;

public interface CommandService {

  void initiateCommand();

  //void processCommand();

  List<Command> getDueClientCommands();

  void processDefaultChats(List<DefaultCommand> dueDefaultCommands, Client client);

  void save(CommandRequest commandRequest) throws Exception;

  void update(CommandRequest commandRequest);

  //void processClientCommand();
}
