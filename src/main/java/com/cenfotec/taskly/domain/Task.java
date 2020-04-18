package com.cenfotec.taskly.domain;

import com.microsoft.azure.spring.data.cosmosdb.core.mapping.Document;
import com.microsoft.azure.spring.data.cosmosdb.core.mapping.PartitionKey;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Document(collection = "Tasks")
public class Task {
    @Id
    private float Id;
    @PartitionKey
    private float userId;
    private String name;
    private String description;
    private boolean complete;
    private LocalDateTime dueDate;
    private LocalDateTime createdDate;

    public Task(float userId, String name, String description, boolean complete, LocalDateTime dueDate) {
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.complete = false;
        this.dueDate = dueDate;
        this.createdDate = LocalDateTime.now();
    }

    public float getId() {
        return Id;
    }

    public void setId(float id) {
        Id = id;
    }

    public float getUserId() {
        return userId;
    }

    public void setUserId(float userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Task{" +
                "Id=" + Id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", complete=" + complete +
                ", dueDate=" + dueDate +
                ", createdDate=" + createdDate +
                '}';
    }
}
