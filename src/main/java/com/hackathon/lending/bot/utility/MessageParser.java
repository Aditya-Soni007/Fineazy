package com.hackathon.lending.bot.utility;

import com.hackathon.lending.bot.dto.MessageContext;
import com.hackathon.lending.bot.dto.WhatsAppWebhookRequest;

public class MessageParser {

    /**
     * Parse WhatsApp webhook request and extract message context
     */
    public static MessageContext parseWebhookMessage(WhatsAppWebhookRequest request) {
        if (request == null || request.getMessages() == null || request.getMessages().isEmpty()) {
            return null;
        }

        WhatsAppWebhookRequest.WhatsAppMessage message = request.getMessages().get(0);

        String userName = "";
        String from = message.getFrom();
        String messageId = message.getId();
        String messageType = message.getType();
        String timestamp = message.getTimestamp();
        String messageBody = extractMessageBody(message);

        // Get userName from contacts if present
        if (request.getContacts() != null && !request.getContacts().isEmpty()) {
            WhatsAppWebhookRequest.Contact contact = request.getContacts().get(0);
            if (contact.getProfile() != null) {
                userName = contact.getProfile().getName();
            }
        }

        return new MessageContext(
                from,
                messageId,
                messageBody,
                messageType,
                userName,
                timestamp
        );
    }

    /**
     * Extract message body based on message type
     */
    private static String extractMessageBody(WhatsAppWebhookRequest.WhatsAppMessage message) {
        if ("text".equals(message.getType()) && message.getText() != null) {
            return message.getText().getBody();
        }
        // You can expand here for other types, e.g. interactive if you start sending them!
        return "";
    }

    private MessageParser() {
        // Utility class
    }
}
