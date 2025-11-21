package com.hackathon.lending.bot.service;

import com.hackathon.lending.bot.dto.MessageContext;
import com.hackathon.lending.bot.entity.ChatHistory;
import com.hackathon.lending.bot.entity.Message;
import com.hackathon.lending.bot.entity.StageTracker;
import com.hackathon.lending.bot.repository.ChatHistoryRepository;
import com.hackathon.lending.bot.repository.MessageRepository;
import com.hackathon.lending.bot.repository.StageTrackerRepository;
import com.hackathon.lending.bot.utility.ApplicationStages;
import com.hackathon.lending.bot.utility.WhatsAppApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class WhatsAppMessageService {
    
    private static final Logger logger = LoggerFactory.getLogger(WhatsAppMessageService.class);
    
    @Autowired
    private ChatHistoryRepository chatHistoryRepository;
    
    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    private StageTrackerRepository stageTrackerRepository;
    
    @Autowired
    private WhatsAppApiClient whatsAppApiClient;
    
    @Autowired
    private LendingWorkflowService lendingWorkflowService;
    
    @Autowired
    private OutgoingMessageService outgoingMessageService;
    
    /**
     * Process incoming WhatsApp message
     */
    public void processIncomingMessage(MessageContext context) {
        try {
            logger.info("Processing message from: {}, Message: {}", context.getFrom(), context.getMessageBody());
            
            // Save message to database
            saveMessage(context);
            
            // Save chat history
            saveChatHistory(context);
            
            // Get or create user stage
            StageTracker stageTracker = getOrCreateStageTracker(context.getFrom());
            
            // Check if message is "Hi SMB Lending chatBot" to trigger welcome message
            String incomingMessage = context.getMessageBody() != null ? context.getMessageBody().trim() : "";
            if (incomingMessage.equalsIgnoreCase("Hi SMB Lending chatBot")) {
                String userName = context.getUserName() != null && !context.getUserName().isEmpty() 
                    ? context.getUserName() 
                    : "there";

                //welcome message , static
                String welcomeMessage = String.format(
                        "Hello %s,\n\nWelcome to the SMB Lending Chatbot from Team PayU Finance!\n\nWe’re here to assist you with your lending needs. How can we help you today?",
                        userName
                );
                
                logger.info("Received trigger message. Sending welcome message via OutgoingMessageService to: {}", context.getFrom());
                outgoingMessageService.sendMessage(context.getFrom(), welcomeMessage);
            }
            
            // Process based on current stage
//            String response = lendingWorkflowService.processUserInput(
//                    context.getFrom(),
//                    context.getMessageBody(),
//                    stageTracker.getCurrentStage()
//            );
//
//            // Send response
//            if (response != null && !response.isEmpty()) {
//                whatsAppApiClient.sendTextMessage(context.getFrom(), response);
//            }
//
//            // Mark message as read
//            whatsAppApiClient.markMessageAsRead(context.getMessageId());
            
        } catch (Exception e) {
            logger.error("Error processing message", e);
            whatsAppApiClient.sendTextMessage(
                    context.getFrom(),
                    "Sorry, we encountered an error processing your request. Please try again."
            );
        }
    }
    
    /**
     * Save message to database
     */
    private void saveMessage(MessageContext context) {
        Message message = new Message();
        message.setMessageId(context.getMessageId());
        message.setMessage(context.getMessageBody());
        messageRepository.save(message);
    }
    /**
     * Save chat history
     */
    private void saveChatHistory(MessageContext context) {
        ChatHistory chatHistory = new ChatHistory();
        chatHistory.setMobileNumber(context.getFrom());
        chatHistory.setName(context.getUserName());
        chatHistory.setMessageId(context.getMessageId());
        chatHistory.setTimeStamp(LocalDateTime.now());
        chatHistory.setMessageType(context.getMessageType());
        chatHistoryRepository.save(chatHistory);
    }
    
    /**
     * Get or create stage tracker for user
     */
    private StageTracker getOrCreateStageTracker(String mobileId) {
        Optional<StageTracker> existingTracker = stageTrackerRepository.findByMobileId(mobileId);
        
        if (existingTracker.isPresent()) {
            return existingTracker.get();
        }
        
        // Create new tracker for new user
        StageTracker newTracker = new StageTracker();
        newTracker.setMobileId(mobileId);
        newTracker.setCurrentStage(ApplicationStages.ONBOARDING);
        return stageTrackerRepository.save(newTracker);
    }
}

