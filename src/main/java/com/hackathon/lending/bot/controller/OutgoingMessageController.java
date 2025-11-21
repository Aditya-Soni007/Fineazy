package com.hackathon.lending.bot.controller;

import com.hackathon.lending.bot.dto.SendMessageRequest;
import com.hackathon.lending.bot.dto.SendMessageResponse;
import com.hackathon.lending.bot.service.OutgoingMessageService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling outgoing WhatsApp messages
 * Provides REST endpoints to send messages via WhatsApp Business API
 */
@RestController
@RequestMapping("/api/v1/messages")
public class OutgoingMessageController {
    
    private static final Logger logger = LoggerFactory.getLogger(OutgoingMessageController.class);
    
    @Autowired
    private OutgoingMessageService outgoingMessageService;
    
    /**
     * Send a text message to a WhatsApp recipient
     * 
     * POST /api/v1/messages/send
     * 
     * Request Body:
     * {
     *   "to": "919876543210",
     *   "message": "Hello from WhatsApp Bot!",
     *   "preview_url": false
     * }
     * 
     * @param request The message request
     * @return ResponseEntity with SendMessageResponse
     */
    @PostMapping("/send")
    public ResponseEntity<SendMessageResponse> sendMessage(@Valid @RequestBody SendMessageRequest request) {
        logger.info("Received request to send message: {}", request);
        
        try {
            SendMessageResponse response = outgoingMessageService.sendMessage(request);
            
            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
        } catch (Exception e) {
            logger.error("Error processing send message request", e);
            SendMessageResponse errorResponse = new SendMessageResponse(
                false, 
                "Internal server error: " + e.getMessage(), 
                request.getTo()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    /**
     * Send a quick text message using query parameters (for simple use cases)
     * 
     * POST /api/v1/messages/send-quick?to=919876543210&message=Hello
     * 
     * @param to Recipient phone number
     * @param message Message content
     * @return ResponseEntity with SendMessageResponse
     */
    @PostMapping("/send-quick")
    public ResponseEntity<SendMessageResponse> sendQuickMessage(
            @RequestParam("to") String to,
            @RequestParam("message") String message) {
        
        logger.info("Received quick message request - To: {}, Message: {}", to, message);
        
        try {
            SendMessageResponse response = outgoingMessageService.sendMessage(to, message);
            
            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
        } catch (Exception e) {
            logger.error("Error processing quick message request", e);
            SendMessageResponse errorResponse = new SendMessageResponse(
                false, 
                "Internal server error: " + e.getMessage(), 
                to
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    /**
     * Health check endpoint
     * 
     * GET /api/v1/messages/health
     * 
     * @return Simple health status
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Outgoing Message Service is running");
    }
}

