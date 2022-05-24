package com.chatbot.chatbot.repository;

import com.chatbot.chatbot.entity.Chat;
import com.chatbot.chatbot.entity.Client;
import com.chatbot.chatbot.entity.Command;
import com.chatbot.chatbot.entity.DefaultCommand;
import com.chatbot.chatbot.model.ChatSender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("select ch from Chat ch where ch.client.id=?1 ")
    Optional<List<Chat>> findChatByClient(Long clientId);

    Optional<List<Chat>> findChatByCommand(Command command);

    @Query("select ch from Chat ch where ch.command.id =?1 and ch.client.id=?2")
    Optional<List<Chat>> findChatByCommandAndClient(Long command, Long client);

  //  Optional<List<Chat>> findChatsByCommandAndClientAndSenderAndSender(DefaultCommand defaultCommand, Client client, ChatSender chatSender);



}
