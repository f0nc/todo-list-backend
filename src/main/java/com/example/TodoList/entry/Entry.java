package com.example.TodoList.entry;

import com.example.TodoList.validation.NotInPast;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

@Entity
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;

    @NotBlank
    private String description;

    @NotInPast
    private Instant dueDateTime;

    protected Entry() {}

    public Entry(String username, String description) {
        this.username = username;
        this.description = description;
    }

    public Entry(String description) {
        this.description = description;
    }

    public Entry(String description, Instant dueDateTime) {
        this.description = description;
        this.dueDateTime = dueDateTime;
    }

    public Long getId() { return id; }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getDueDateTime() {
        return dueDateTime;
    }

    public void setDueDateTime(Instant dueDateTime) {
        this.dueDateTime = dueDateTime;
    }
}
