package com.chatbot.chatbot.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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

}
