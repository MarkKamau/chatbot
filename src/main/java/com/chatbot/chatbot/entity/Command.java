package com.chatbot.chatbot.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="command")
@NoArgsConstructor
@RequiredArgsConstructor
public class Command {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee")
    private Employee employee;

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client")
    private Client client;

    @NonNull
    @Column(name = "wait_time")
    private Long waitTime;

    @NonNull
    @Column(name = "message_type")
    private String messageType;

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "template")
    private Template template;

}
