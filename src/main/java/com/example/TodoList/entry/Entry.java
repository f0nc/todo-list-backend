package com.example.TodoList.entry;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String description;

    protected Entry() {}

    public Entry(String username, String description) {
        this.username = username;
        this.description = description;
    }

    public Entry(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getUsername() {
        return username;
    }

    public Long getId() { return id; }

    public void setUsername(String username) {
        this.username = username;
    }
}
