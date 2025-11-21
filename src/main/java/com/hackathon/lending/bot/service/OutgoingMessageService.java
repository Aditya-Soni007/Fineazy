package com.hackathon.lending.bot.service;

import com.hackathon.lending.bot.dto.SendMessageRequest;
import com.hackathon.lending.bot.dto.SendMessageResponse;
import com.hackathon.lending.bot.utility.WhatsAppApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for handling outgoing WhatsApp messages
 */
@Service
public class OutgoingMessageService {
    
    private static final Logger logger = LoggerFactory.getLogger(OutgoingMessageService.class);
    
    @Autowired
    private WhatsAppApiClient whatsAppApiClient;
    
    /**
     * Send a text message to a WhatsApp recipient
     * 
     * @param request The message request containing recipient and message content
     * @return SendMessageResponse with the result of the operation
     */
    public SendMessageResponse sendMessage(SendMessageRequest request) {
        logger.info("Attempting to send message to: {}", request.getTo());
        
        try {
            // Validate input
            if (request.getTo() == null || request.getTo().isEmpty()) {
                logger.error("Recipient phone number is empty");
                return new SendMessageResponse(false, "Recipient phone number is required", null);
            }
            
            if (request.getMessage() == null || request.getMessage().isEmpty()) {
                logger.error("Message body is empty");
                return new SendMessageResponse(false, "Message body is required", null);
            }
            
            // Send message via WhatsApp API Client
            boolean success = whatsAppApiClient.sendTextMessage(request.getTo(), request.getMessage());
            
            if (success) {
                logger.info("Message sent successfully to: {}", request.getTo());
                return new SendMessageResponse(
                    true, 
                    "Message sent successfully", 
                    request.getTo()
                );
            } else {
                logger.error("Failed to send message to: {}", request.getTo());
                return new SendMessageResponse(
                    false, 
                    "Failed to send message. Please check logs for details.", 
                    request.getTo()
                );
            }
            
        } catch (Exception e) {
            logger.error("Error occurred while sending message to: {}", request.getTo(), e);
            return new SendMessageResponse(
                false, 
                "Error occurred: " + e.getMessage(), 
                request.getTo()
            );
        }
    }
    
    /**
     * Send a text message with specified parameters
     * 
     * @param to The recipient phone number
     * @param message The message content
     * @return SendMessageResponse with the result of the operation
     */
    public SendMessageResponse sendMessage(String to, String message) {
        SendMessageRequest request = new SendMessageRequest(to, message);
        return sendMessage(request);
    }
    
    /**
     * Send a text message with preview URL option
     * 
     * @param to The recipient phone number
     * @param message The message content
     * @param previewUrl Whether to enable URL preview
     * @return SendMessageResponse with the result of the operation
     */
    public SendMessageResponse sendMessage(String to, String message, boolean previewUrl) {
        SendMessageRequest request = new SendMessageRequest(to, message, previewUrl);
        return sendMessage(request);
    }
}

