package com.chatbot.chatbot.entity;


import lombok.*;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Setter
@Entity
@Table(name="chatsession")
@NoArgsConstructor
@RequiredArgsConstructor
public class ChatSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client")
    private Client client;

    @NonNull
    @Column(name = "date_started")
    private LocalDateTime dateStarted;

    @Transient
    private Long timeInSession;

    @Transient
    private Command currentCommand;

    public Long getTimeInSession() {
        return Duration.between(dateStarted, LocalDateTime.now()).toMinutes();
    }

    public Optional<Command> getCurrentCommand(){
       return client.getCommands().stream().filter(command -> (command.getWaitTime()==timeInSession)).findAny();
    }
}
