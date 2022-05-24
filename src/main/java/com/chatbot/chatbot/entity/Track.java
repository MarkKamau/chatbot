package com.chatbot.chatbot.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="track")
@NoArgsConstructor
@RequiredArgsConstructor
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @Column(name = "keyWord")
    private String keyWords;

    @NonNull
    @Column(name = "start")
    private boolean start;

}
