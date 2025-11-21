package com.hackathon.lending.bot.service;

import com.hackathon.lending.bot.entity.ConfigProperty;
import com.hackathon.lending.bot.repository.ConfigPropertyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfigService {

    private static final Logger logger = LoggerFactory.getLogger(ConfigService.class);

    private final ConfigPropertyRepository configPropertyRepository;

    @Value("${whatsapp.access.token:}")
    private String fallbackAccessTokenFromProperties;

    public ConfigService(ConfigPropertyRepository configPropertyRepository) {
        this.configPropertyRepository = configPropertyRepository;
    }

    public Optional<String> getConfigValue(String key) {
        return configPropertyRepository.findById(key).map(ConfigProperty::getValue);
    }

    public String getRequiredConfigValue(String key) {
        Optional<String> valueFromDb = getConfigValue(key);
        if (valueFromDb.isPresent()) {
            return valueFromDb.get();
        }

        // Fallback to application.properties for local/dev safety
        if (!fallbackAccessTokenFromProperties.isEmpty()
                && "whatsapp.access.token".equals(key)) {
            logger.warn(
                    "Config key '{}' not found in DB. Falling back to value from application.properties. " +
                    "Consider inserting it into 'config_properties' table for dynamic updates.",
                    key
            );
            return fallbackAccessTokenFromProperties;
        }

        logger.error("Required config key '{}' not found in DB and no fallback available", key);
        throw new IllegalStateException("Missing required configuration: " + key);
    }
}


