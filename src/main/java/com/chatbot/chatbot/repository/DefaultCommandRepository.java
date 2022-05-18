package com.chatbot.chatbot.repository;

import com.chatbot.chatbot.entity.ClientCommand;
import com.chatbot.chatbot.entity.DefaultCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DefaultCommandRepository extends JpaRepository<DefaultCommand, Long> {


    List<ClientCommand> findCommandByWaitTime(Long waitTime);
}
