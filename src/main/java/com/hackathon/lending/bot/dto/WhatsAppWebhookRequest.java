package com.hackathon.lending.bot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class WhatsAppWebhookRequest {

    @JsonProperty("contacts")
    private List<Contact> contacts;

    @JsonProperty("messages")
    private List<WhatsAppMessage> messages;

    // ------------ Contacts & Messages Inner Classes -----------

    public static class Contact {
        @JsonProperty("profile")
        private Profile profile;

        @JsonProperty("wa_id")
        private String waId;
        // Getters & setters...
        public Profile getProfile() { return profile; }
        public void setProfile(Profile profile) { this.profile = profile; }
        public String getWaId() { return waId; }
        public void setWaId(String waId) { this.waId = waId; }
    }

    public static class Profile {
        @JsonProperty("name")
        private String name;
        // Getters & setters...
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    public static class WhatsAppMessage {
        @JsonProperty("from")
        private String from;

        @JsonProperty("id")
        private String id;

        @JsonProperty("timestamp")
        private String timestamp;

        @JsonProperty("text")
        private Text text;

        @JsonProperty("type")
        private String type;

        // Getters & setters...
        public String getFrom() { return from; }
        public void setFrom(String from) { this.from = from; }
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getTimestamp() { return timestamp; }
        public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
        public Text getText() { return text; }
        public void setText(Text text) { this.text = text; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
    }

    public static class Text {
        @JsonProperty("body")
        private String body;
        // Getters & setters...
        public String getBody() { return body; }
        public void setBody(String body) { this.body = body; }
    }

    // ---------- Main class getters & setters ------------

    public List<Contact> getContacts() { return contacts; }
    public void setContacts(List<Contact> contacts) { this.contacts = contacts; }

    public List<WhatsAppMessage> getMessages() { return messages; }
    public void setMessages(List<WhatsAppMessage> messages) { this.messages = messages; }
}
