package com.chatbot.chatbot.service;

import com.chatbot.chatbot.entity.Command;
import com.chatbot.chatbot.model.CommandRequest;

import java.util.List;

public interface CommandService {

  void initiateCommand();

  void processCommand();

  List<Command> getDueComamnds();

  void processChat(List<Command> dueCommands);

  void save(CommandRequest commandRequest) throws Exception;

  void update(CommandRequest commandRequest);
}
