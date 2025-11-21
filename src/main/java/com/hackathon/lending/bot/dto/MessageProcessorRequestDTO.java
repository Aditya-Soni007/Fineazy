package com.hackathon.lending.bot.dto;

import com.hackathon.lending.bot.entity.UserDetails;

public class MessageProcessorRequestDTO {
    
    private MessageContext messageContext;
    private UserDetails userDetails;
    
    public MessageProcessorRequestDTO() {
    }
    
    public MessageProcessorRequestDTO(MessageContext messageContext, UserDetails userDetails) {
        this.messageContext = messageContext;
        this.userDetails = userDetails;
    }
    
    public static MessageProcessorRequestDTO of(MessageContext messageContext, UserDetails userDetails) {
        return new MessageProcessorRequestDTO(messageContext, userDetails);
    }
    
    public MessageContext getMessageContext() {
        return messageContext;
    }
    
    public void setMessageContext(MessageContext messageContext) {
        this.messageContext = messageContext;
    }
    
    public UserDetails getUserDetails() {
        return userDetails;
    }
    
    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }
}
