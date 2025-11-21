package com.hackathon.lending.bot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO for the response after sending a WhatsApp message
 */
public class SendMessageResponse {
    
    @JsonProperty("success")
    private boolean success;
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("recipient")
    private String recipient;
    
    @JsonProperty("timestamp")
    private String timestamp;
    
    public SendMessageResponse() {
    }
    
    public SendMessageResponse(boolean success, String message, String recipient) {
        this.success = success;
        this.message = message;
        this.recipient = recipient;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }
    
    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getRecipient() {
        return recipient;
    }
    
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    
    public String getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    @Override
    public String toString() {
        return "SendMessageResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", recipient='" + recipient + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}

