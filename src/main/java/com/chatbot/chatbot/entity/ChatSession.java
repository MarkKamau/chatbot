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

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "track")
    private Track currentTrack;

    @Transient
    private Long timeInSession;

    @Transient
    private Command currentCommand;


    public Long getTimeInSession() {
        timeInSession= Duration.between(dateStarted, LocalDateTime.now()).toMinutes();
        return timeInSession;
    }

    public Optional<Command> getCurrentClientCommand(){
       Optional<ClientCommand> optionalCommand= client.getClientCommands().stream().filter(clientCommand -> (clientCommand.getWaitTime()==getTimeInSession())).findAny();
       return optionalCommand.isPresent()? Optional.of((Command) optionalCommand.get()) :Optional.empty();
    }
}
