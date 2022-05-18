package com.chatbot.chatbot.service;

import com.chatbot.chatbot.entity.Chat;
import com.chatbot.chatbot.entity.Command;

import java.util.ArrayList;
import java.util.List;

public class ProcessBotMessage implements Runnable {

    final List<Command> commands;

    public ProcessBotMessage(List<Command> commands){
        this.commands = commands;
    }

    @Override
    public void run() {
        List<Chat> chats=new ArrayList<>();
        for (Command command:commands) {
            String chatMessage=constructMessage(command);
            sendMessage(chatMessage);


        }
    }

    private String constructMessage(Command command){
/*        String template = command.getTemplate().getValue();

        String templateResponse=template.replace("Employee.FirstName", command.getEmployee().getFirstName());
        templateResponse=templateResponse.replace("Employee.LastName", command.getEmployee().getLastName());
        templateResponse=templateResponse.replace("Client.FirstName", command.getClient().getFirstName());
        templateResponse=templateResponse.replace("Client.LastName", command.getClient().getLastName());
        return templateResponse;*/
        return "";
    }

    private void sendMessage(String chat){
        //TODO Send and Save message to user number;
    }
}
