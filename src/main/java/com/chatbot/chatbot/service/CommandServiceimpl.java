package com.chatbot.chatbot.service;

import com.chatbot.chatbot.entity.*;
import com.chatbot.chatbot.model.ChatSender;
import com.chatbot.chatbot.model.CommandRequest;
import com.chatbot.chatbot.repository.ChatRepository;
import com.chatbot.chatbot.repository.ClientCommandRepository;
import com.chatbot.chatbot.repository.DefaultCommandRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommandServiceimpl implements  CommandService{

    private final ClientCommandRepository clientCommandRepository;

    private final DefaultCommandRepository defaultCommandRepository;

    private final ChatRepository chatRepository;

    private final ChatService chatService;

    private final ClientService clientService;

    private final EmployeeService employeeService;

    private final TemplateService templateService;

    private final ChatSessionService chatSessionService;

    private static LocalDateTime startTime=LocalDateTime.now();

    public CommandServiceimpl(ClientCommandRepository clientCommandRepository, DefaultCommandRepository defaultCommandRepository, ChatRepository chatRepository, ChatService chatService, ClientService clientService, EmployeeService employeeService, TemplateService templateService, ChatSessionService chatSessionService) {
        this.clientCommandRepository = clientCommandRepository;
        this.defaultCommandRepository = defaultCommandRepository;
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
        processClientCommand();
        processDefaultCommand();
    }

    private void processDefaultCommand(){
        List<DefaultCommand> defaultCommands= defaultCommandRepository.findAll();
        List<Client> clients = chatSessionService.getChatSessions()
                .stream()
                .filter(chatSession ->
                                defaultCommands
                                        .stream()
                                        .filter(defaultCommand ->
                                                defaultCommand.getWaitTime()==chatSession.getTimeInSession()).findAny().isPresent()

                        ).map(chatSession -> chatSession.getClient()).collect(Collectors.toList());

        clients.forEach(client -> processDefaultChats(defaultCommands, client));

    }

    private void processClientCommand() {
        List<Command> dueCommands = getDueClientCommands();

        if (dueCommands.isEmpty()){
            return;
        }

        dueCommands.stream().forEach(command -> processChat(command));

        //processChat(dueCommands);
    }
    private void processChat(Command dueCommand){
        ClientCommand clientCommand=(ClientCommand) dueCommand;
        if (canProcessChat(dueCommand, clientCommand.getClient())) {
            String chatMessage = constructMessage(((ClientCommand) dueCommand).getEmployee(), ((ClientCommand) dueCommand).getClient(),dueCommand.getTemplate().getValue());
            sendMessage(chatMessage);
            chatService.saveChat(new Chat(((ClientCommand) dueCommand).getClient(),dueCommand, chatMessage, ChatSender.BOT, LocalDateTime.now()));
        }
    }

    public void processDefaultChats(List<DefaultCommand> dueDefaultCommands, Client client){
        List<Chat> chats=new ArrayList<>();
        for (Command command:dueDefaultCommands) {
            if (canProcessChat(command, client)){
                String chatMessage=constructMessage(((DefaultCommand) command).getEmployee(), client,command.getTemplate().getValue());;
                sendMessage(chatMessage);

                chats.add(new Chat(client,command, chatMessage, ChatSender.BOT, LocalDateTime.now()));
            }
        }
        chatService.saveChats(chats);
    }

    private boolean canProcessChat(Command command, Client client){
        return !commandIsProcessedForClient(command, client) && !clientHasResponded(command, client);
    }

    private boolean clientHasResponded(Command command, Client client){
            return chatRepository.findChatByCommandAndClient(command.getId(), client.getId())
                    .orElse(Collections.emptyList())
                    .stream()
                    .filter(chat -> chat.getSender().equals(ChatSender.CLIENT))
                    .max(Comparator.comparing(Chat::getId)).isPresent();
    }

    private boolean commandIsProcessedForClient(Command command, Client client){
        List<Chat> chats=chatRepository.findChatByCommandAndClient(command.getId(), client.getId()).get();
        return !chats.isEmpty();
    }

    @Override
    public List<Command> getDueClientCommands() {
        List<Command> commands= chatSessionService.getChatSessionsWithActiveClientCommands()
                .stream()
                .map(chatSession -> chatSession.getCurrentClientCommand().get())
                .collect(Collectors.toList());
        return commands;
    }


    private String constructMessage(Employee employee, Client client, String template){
        String templateResponse=template.replace("<Employee.FirstName>", employee.getFirstName());
        templateResponse=templateResponse.replace("<Employee.LastName>", employee.getLastName());
        templateResponse=templateResponse.replace("<Client.FirstName>",client.getFirstName());
        templateResponse=templateResponse.replace("<Client.LastName>", client.getLastName());
        return templateResponse;
    }

    private void sendMessage(String chat){
        //TODO Send and Save message to user number;
    }

    public void save(CommandRequest commandRequest) throws Exception {
        Client client = clientService.findClientById(commandRequest.getClient()).orElseThrow(() -> new Exception("Client not found"));
        Employee employee = employeeService.findEmployeeById(commandRequest.getEmployee()).orElseThrow(() -> new Exception("Employee not found"));
        Template template = templateService.findTemplateById(commandRequest.getTemplate()).orElseThrow(() -> new Exception("Template not found"));
        clientCommandRepository.save(new ClientCommand(employee, client,commandRequest.getWaitTime(),commandRequest.getMessageType(), template));
    }

    public void update(CommandRequest commandRequest){
        //TODO Should a command be updated?
    }


}
