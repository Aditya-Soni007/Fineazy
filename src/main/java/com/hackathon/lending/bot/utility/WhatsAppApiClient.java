package com.hackathon.lending.bot.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathon.lending.bot.dto.WhatsAppMessageRequest;
import com.hackathon.lending.bot.service.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class WhatsAppApiClient {

    private static final Logger logger = LoggerFactory.getLogger(WhatsAppApiClient.class);

    private final String phoneNumberId;
    private final String apiUrl;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final ConfigService configService;

    public WhatsAppApiClient(
            ConfigService configService,
            @org.springframework.beans.factory.annotation.Value("${whatsapp.phone.number.id}") String phoneNumberId,
            @org.springframework.beans.factory.annotation.Value("${whatsapp.api.url}") String apiUrl) {
        this.configService = configService;
        this.phoneNumberId = phoneNumberId;
        this.apiUrl = apiUrl;
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Send a text message to a WhatsApp user
     */
    public boolean sendTextMessage(String to, String message) {
        try {
            WhatsAppMessageRequest request = new WhatsAppMessageRequest(to, message);
            String requestBody = objectMapper.writeValueAsString(request);

            String url = String.format("%s/%s/messages", apiUrl, phoneNumberId);

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + resolveAccessToken())
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                logger.info("Message sent successfully to {}", to);
                return true;
            } else {
                logger.error("Failed to send message. Status: {}, Body: {}", response.statusCode(), response.body());
                return false;
            }

        } catch (Exception e) {
            logger.error("Error sending WhatsApp message", e);
            return false;
        }
    }

    /**
     * Mark a message as read
     */
    public void markMessageAsRead(String messageId) {
        try {
            String url = String.format("%s/%s/messages", apiUrl, phoneNumberId);
            String requestBody = String.format(
                    "{\"messaging_product\":\"whatsapp\",\"status\":\"read\",\"message_id\":\"%s\"}", messageId);

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + resolveAccessToken())
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            logger.debug("Message {} marked as read", messageId);

        } catch (Exception e) {
            logger.error("Error marking message as read", e);
        }
    }

    private String resolveAccessToken() {
        String token = configService.getRequiredConfigValue("whatsapp.access.token");
        if (token != null && !token.isEmpty()) {
            return token;
        }
        logger.error("Resolved WhatsApp access token is null/empty");
        throw new IllegalStateException("WhatsApp access token is not configured");
    }
}
