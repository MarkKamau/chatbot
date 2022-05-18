package com.chatbot.chatbot.entity;

import com.chatbot.chatbot.model.ChatSender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name="chat")
@NoArgsConstructor
@RequiredArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "client")
    private Client client;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "command")
    private Command command;

    @NonNull
    @Column(name = "message")
    private String message;

    @NonNull
    @Column(name = "sender")
    @Enumerated(EnumType.STRING)
    private ChatSender sender;

    @NonNull
    @Column(name = "date_created")
    private LocalDateTime dateCreated;
}
