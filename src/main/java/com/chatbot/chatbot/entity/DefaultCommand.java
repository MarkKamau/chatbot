package com.chatbot.chatbot.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@DiscriminatorValue("DEFAULT-COMMAND")
public class DefaultCommand  extends  Command{

    public DefaultCommand(@NonNull Employee employee,  @NonNull Long waitTime, @NonNull String messageType, @NonNull Template template, @NonNull Track track) {
        super( waitTime, messageType, template, track);
        this.employee = employee;
    }

    @NonNull
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee")
    private Employee employee;

}
