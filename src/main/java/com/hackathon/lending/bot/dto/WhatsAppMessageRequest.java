package com.hackathon.lending.bot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WhatsAppMessageRequest {
    
    @JsonProperty("messaging_product")
    private String messagingProduct = "whatsapp";
    
    @JsonProperty("recipient_type")
    private String recipientType = "individual";
    
    @JsonProperty("to")
    private String to;
    
    @JsonProperty("type")
    private String type;
    
    @JsonProperty("text")
    private TextContent text;
    
    public WhatsAppMessageRequest() {
    }
    
    public WhatsAppMessageRequest(String to, String messageBody) {
        this.to = to;
        this.type = "text";
        this.text = new TextContent(messageBody);
    }
    
    // Getters and Setters
    public String getMessagingProduct() {
        return messagingProduct;
    }
    
    public void setMessagingProduct(String messagingProduct) {
        this.messagingProduct = messagingProduct;
    }
    
    public String getRecipientType() {
        return recipientType;
    }
    
    public void setRecipientType(String recipientType) {
        this.recipientType = recipientType;
    }
    
    public String getTo() {
        return to;
    }
    
    public void setTo(String to) {
        this.to = to;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public TextContent getText() {
        return text;
    }
    
    public void setText(TextContent text) {
        this.text = text;
    }
    
    public static class TextContent {
        @JsonProperty("preview_url")
        private Boolean previewUrl = false;
        
        @JsonProperty("body")
        private String body;
        
        public TextContent() {
        }
        
        public TextContent(String body) {
            this.body = body;
        }
        
        public TextContent(String body, Boolean previewUrl) {
            this.body = body;
            this.previewUrl = previewUrl;
        }
        
        public Boolean getPreviewUrl() {
            return previewUrl;
        }
        
        public void setPreviewUrl(Boolean previewUrl) {
            this.previewUrl = previewUrl;
        }
        
        public String getBody() {
            return body;
        }
        
        public void setBody(String body) {
            this.body = body;
        }
    }
}

