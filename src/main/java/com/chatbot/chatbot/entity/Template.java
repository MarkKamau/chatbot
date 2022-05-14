package com.chatbot.chatbot.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="template")
@RequiredArgsConstructor
@NoArgsConstructor
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @Column(name = "value")
    private String value;
}
