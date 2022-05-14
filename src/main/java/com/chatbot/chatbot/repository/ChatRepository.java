package com.chatbot.chatbot.repository;

import com.chatbot.chatbot.entity.Chat;
import com.chatbot.chatbot.entity.Command;
import com.chatbot.chatbot.model.ChatSender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("select ch from Chat ch join ch.command cd where cd.client.id=?1")
    Optional<List<Chat>> findChatByClient(Long clientId);

    Optional<Chat> findChatByCommand(Command command);

    @Query("select ch from Chat ch where ch.command.id =?1 and ch.sender=?2")
    Optional<Chat> findChatByCommandAndSender(Long command, String sender);

    Optional<List<Chat>> findChatsByCommandAndSender(Long command, ChatSender chatSender);



}
