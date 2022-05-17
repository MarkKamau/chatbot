package com.chatbot.chatbot.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="client")
@RequiredArgsConstructor
@NoArgsConstructor
public class Client {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NonNull
    @Column(name = "customer_number")
    private String customerNumber;

    @NonNull
    @Column(name = "first_name")
    private String firstName;

    @NonNull
    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "client", fetch=FetchType.EAGER)
    private List<Command> commands=new ArrayList<>();

}
