package com.chatbot.chatbot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@DiscriminatorValue("CLIENT-COMMAND")
public class ClientCommand extends  Command{

    public ClientCommand(@NonNull Employee employee, @NonNull Client client, @NonNull Long waitTime, @NonNull String messageType, @NonNull Template template) {
        super( waitTime, messageType, template);
        this.client = client;
        this.employee=employee;
    }


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee")
    @JsonIgnore
    private Employee employee;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client")
    @JsonIgnore
    private Client client;

}
