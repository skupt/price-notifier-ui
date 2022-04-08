package com.example.pricenotifierui.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public abstract class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long externalId;
    private String username;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(40) default 'NEW'")
    private TaskStatus taskStatus;
}
