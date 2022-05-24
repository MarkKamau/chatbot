package com.chatbot.chatbot.service;

import com.chatbot.chatbot.entity.*;
import com.chatbot.chatbot.model.ChatSender;
import com.chatbot.chatbot.model.ClientCommandRequest;
import com.chatbot.chatbot.model.DefaultCommandRequest;
import com.chatbot.chatbot.repository.ChatRepository;
import com.chatbot.chatbot.repository.ClientCommandRepository;
import com.chatbot.chatbot.repository.DefaultCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
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

    private final TrackService trackService;

/*    public CommandServiceimpl(ClientCommandRepository clientCommandRepository, DefaultCommandRepository defaultCommandRepository, ChatRepository chatRepository, ChatService chatService, ClientService clientService, EmployeeService employeeService, TemplateService templateService, ChatSessionService chatSessionService) {
        this.clientCommandRepository = clientCommandRepository;
        this.defaultCommandRepository = defaultCommandRepository;
        this.chatRepository = chatRepository;
        this.chatService = chatService;
        this.clientService = clientService;
        this.employeeService = employeeService;
        this.templateService = templateService;
        this.chatSessionService = chatSessionService;
    }*/

    @Scheduled(fixedDelay = 10000)
    @Override
    public void initiateCommand() {
        processClientCommand();
        processDefaultCommand();
    }

    private void processDefaultCommand(){
        List<DefaultCommand> defaultCommands= defaultCommandRepository.findAll();
        chatSessionService.getChatSessions()
                .stream()
                .forEach(chatSession ->
                                defaultCommands
                                        .stream()
                                        .forEach(defaultCommand ->
                                        {
                                            if (defaultCommand.getWaitTime()==chatSession.getTimeInSession() &&
                                                    chatSession.getCurrentTrack().getId()==defaultCommand.getTrack().getId()) {
                                                processDefaultChats(Arrays.asList(defaultCommand), chatSession.getClient());
                                            }
                                        })
                        );
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

    private boolean canProcessChat(Command command, Client client) {
        return !commandIsProcessedForClient(command, client) && !clientHasResponded(command, client);
    }

    private boolean clientHasResponded(Command command, Client client)  {

            ChatSession chatSession=chatSessionService.findCurrentClientChatSession(client).get();
            return chatRepository.findChatByClient(client.getId())
                    .orElse(Collections.emptyList())
                    .stream()
                    .filter(chat -> chat.getSender().equals(ChatSender.CLIENT) && chat.getCommand().getTrack().getId()==chatSession.getCurrentTrack().getId())
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

    public void createClientCommand(ClientCommandRequest clientCommandRequest) throws Exception {
        Client client = clientService.findClientById(clientCommandRequest.getClient()).orElseThrow(() -> new Exception("Client not found"));
        Employee employee = employeeService.findEmployeeById(clientCommandRequest.getEmployee()).orElseThrow(() -> new Exception("Employee not found"));
        Template template = templateService.findTemplateById(clientCommandRequest.getTemplate()).orElseThrow(() -> new Exception("Template not found"));
        Track track = trackService.getTrackById(clientCommandRequest.getTrack()).orElseThrow(() -> new Exception("Track not found"));
        clientCommandRepository.save(new ClientCommand(employee, client,clientCommandRequest.getWaitTime(),clientCommandRequest.getMessageType(), template, track));
    }

    public void createDefaultCommand(DefaultCommandRequest defaultCommandRequest) throws Exception {
        Employee employee = employeeService.findEmployeeById(defaultCommandRequest.getEmployee()).orElseThrow(() -> new Exception("Employee not found"));
        Template template = templateService.findTemplateById(defaultCommandRequest.getTemplate()).orElseThrow(() -> new Exception("Template not found"));
        Track track = trackService.getTrackById(defaultCommandRequest.getTrack()).orElseThrow(() -> new Exception("Track not found"));
        defaultCommandRepository.save(new DefaultCommand(employee, defaultCommandRequest.getWaitTime(),defaultCommandRequest.getMessageType(), template, track));
    }

    public void update(ClientCommandRequest commandRequest){
        //TODO Should a command be updated?
    }


}
