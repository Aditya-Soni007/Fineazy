package com.hackathon.lending.bot.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_history")
public class ChatHistory {
    
    @Id
    @Column(name = "mobile_number", nullable = false, length = 20)
    private String mobileNumber;
    
    @Column(name = "name", length = 100)
    private String name;
    
    @Column(name = "message_id", length = 100)
    private String messageId;
    
    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;
    
    @Column(name = "message_type", length = 50)
    private String messageType;
    
    // Constructors
    public ChatHistory() {
    }
    
    public ChatHistory(String mobileNumber, String name, String messageId, LocalDateTime timeStamp, String messageType) {
        this.mobileNumber = mobileNumber;
        this.name = name;
        this.messageId = messageId;
        this.timeStamp = timeStamp;
        this.messageType = messageType;
    }
    
    // Getters and Setters
    public String getMobileNumber() {
        return mobileNumber;
    }
    
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getMessageId() {
        return messageId;
    }
    
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
    
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
    
    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
    
    public String getMessageType() {
        return messageType;
    }
    
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}

