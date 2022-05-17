package com.chatbot.chatbot.service;

import com.chatbot.chatbot.entity.*;
import com.chatbot.chatbot.model.ChatSender;
import com.chatbot.chatbot.model.CommandRequest;
import com.chatbot.chatbot.repository.ChatRepository;
import com.chatbot.chatbot.repository.CommandRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommandServiceimpl implements  CommandService{

    private final CommandRepository commandRepository;

    private final ChatRepository chatRepository;

    private final ChatService chatService;

    private final ClientService clientService;

    private final EmployeeService employeeService;

    private final TemplateService templateService;

    private final ChatSessionService chatSessionService;

    private static LocalDateTime startTime=LocalDateTime.now();

    public CommandServiceimpl(CommandRepository commandRepository, ChatRepository chatRepository, ChatService chatService, ClientService clientService, EmployeeService employeeService, TemplateService templateService, ChatSessionService chatSessionService) {
        this.commandRepository = commandRepository;
        this.chatRepository = chatRepository;
        this.chatService = chatService;
        this.clientService = clientService;
        this.employeeService = employeeService;
        this.templateService = templateService;
        this.chatSessionService = chatSessionService;
    }

    @Scheduled(fixedDelay = 10000)
    @Override
    public void initiateCommand() {
        processCommand();
    }

    @Override
    public void processCommand() {
        List<Command> dueCommands =getDueComamnds();
        if (dueCommands.isEmpty()){
            return;
        }

        dueCommands.stream().forEach(command -> processChat(command));

        processChat(dueCommands);
    }
    private void processChat(Command dueCommand){
        if (canProcessChat(dueCommand)) {
            String chatMessage = constructMessage(dueCommand);
            sendMessage(chatMessage);
            chatService.saveChat(new Chat(dueCommand, chatMessage, ChatSender.BOT, LocalDateTime.now()));
        }
    }

    public void processChat(List<Command> dueCommands){
        List<Chat> chats=new ArrayList<>();
        for (Command command:dueCommands) {
            if (canProcessChat(command)){
                String chatMessage=constructMessage(command);
                sendMessage(chatMessage);

                chats.add(new Chat(command, chatMessage, ChatSender.BOT, LocalDateTime.now()));
            }
        }
        chatService.saveChats(chats);
    }

    private boolean canProcessChat(Command command){
        return !commandIsProcessedForClient(command) && !clientHasResponded(command);
    }

    private boolean clientHasResponded(Command command){
        return chatRepository.findChatByClient(command.getClient().getId())
                .orElse(Collections.emptyList())
                .stream()
                .filter(chat -> chat.getSender().equals(ChatSender.CLIENT))
                .max(Comparator.comparing(Chat::getId)).isPresent();
    }

    private boolean commandIsProcessedForClient(Command command){
        List<Chat> chats=chatRepository.findChatByCommand(command).get();
        return !chats.isEmpty();//chatRepository.findChatByCommand(command).isPresent();
    }

    @Override
    public List<Command> getDueComamnds() {
        return chatSessionService.getActiveChatSessions()
                .stream()
                    .map(chatSession -> chatSession.getCurrentCommand().get())
                        .collect(Collectors.toList());
    }


    private String constructMessage(Command command){
        String template = command.getTemplate().getValue();

        String templateResponse=template.replace("<Employee.FirstName>", command.getEmployee().getFirstName());
        templateResponse=templateResponse.replace("<Employee.LastName>", command.getEmployee().getLastName());
        templateResponse=templateResponse.replace("<Client.FirstName>", command.getClient().getFirstName());
        templateResponse=templateResponse.replace("<Client.LastName>", command.getClient().getLastName());
        return templateResponse;
    }

    private void sendMessage(String chat){
        //TODO Send and Save message to user number;
    }

    public void save(CommandRequest commandRequest) throws Exception {
        Client client = clientService.findClientById(commandRequest.getClient()).orElseThrow(() -> new Exception("Client not found"));
        Employee employee = employeeService.findEmployeeById(commandRequest.getEmployee()).orElseThrow(() -> new Exception("Employee not found"));
        Template template = templateService.findTemplateById(commandRequest.getTemplate()).orElseThrow(() -> new Exception("Template not found"));
        commandRepository.save(new Command(employee, client,commandRequest.getWaitTime(),commandRequest.getMessageType(), template));
    }

    public void update(CommandRequest commandRequest){
        //TODO Should a command be updated?
    }


}
