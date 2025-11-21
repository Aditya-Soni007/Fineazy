package com.hackathon.lending.bot.dto;

public class MessageContext {
    
    private String from;
    private String messageId;
    private String messageBody;
    private String messageType;
    private String userName;
    private String timestamp;
    
    public MessageContext() {
    }
    
    public MessageContext(String from, String messageId, String messageBody, String messageType, String userName, String timestamp) {
        this.from = from;
        this.messageId = messageId;
        this.messageBody = messageBody;
        this.messageType = messageType;
        this.userName = userName;
        this.timestamp = timestamp;
    }
    
    // Getters and Setters
    public String getFrom() {
        return from;
    }
    
    public void setFrom(String from) {
        this.from = from;
    }
    
    public String getMessageId() {
        return messageId;
    }
    
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
    
    public String getMessageBody() {
        return messageBody;
    }
    
    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }
    
    public String getMessageType() {
        return messageType;
    }
    
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

