package com.chatbot.chatbot.repository;


import com.chatbot.chatbot.entity.ChatSession;
import com.chatbot.chatbot.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatSessionRepository extends JpaRepository<ChatSession, Long> {

    Optional<ChatSession> findChatSessionByClient(Client client);
}
