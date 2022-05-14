package com.chatbot.chatbot.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="employee")
@NoArgsConstructor
@RequiredArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NonNull
    @Column(name = "first_name")
    private String firstName;

    @NonNull
    @Column(name = "last_name")
    private String lastName;
}
