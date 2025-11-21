package com.hackathon.lending.bot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for sending outgoing WhatsApp messages via REST API
 */
public class SendMessageRequest {
    
    @NotBlank(message = "Recipient phone number is required")
    @JsonProperty("to")
    private String to;
    
    @NotBlank(message = "Message body is required")
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("preview_url")
    private boolean previewUrl = false;
    
    public SendMessageRequest() {
    }
    
    public SendMessageRequest(String to, String message) {
        this.to = to;
        this.message = message;
    }
    
    public SendMessageRequest(String to, String message, boolean previewUrl) {
        this.to = to;
        this.message = message;
        this.previewUrl = previewUrl;
    }
    
    // Getters and Setters
    public String getTo() {
        return to;
    }
    
    public void setTo(String to) {
        this.to = to;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public boolean isPreviewUrl() {
        return previewUrl;
    }
    
    public void setPreviewUrl(boolean previewUrl) {
        this.previewUrl = previewUrl;
    }
    
    @Override
    public String toString() {
        return "SendMessageRequest{" +
                "to='" + to + '\'' +
                ", message='" + message + '\'' +
                ", previewUrl=" + previewUrl +
                '}';
    }
}

