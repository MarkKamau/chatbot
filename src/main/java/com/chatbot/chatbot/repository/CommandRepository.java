package com.chatbot.chatbot.repository;

import com.chatbot.chatbot.entity.Command;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandRepository  extends JpaRepository<Command, Long> {


    List<Command> findCommandByWaitTime(Long waitTime);
}
