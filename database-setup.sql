-- WhatsApp Lending Bot - Database Setup Script
-- Run this script to manually create tables (optional, as Hibernate will auto-create them)
-- Create database
CREATE DATABASE IF NOT EXISTS whatsapp_bot;

USE whatsapp_bot;

-- Table: chat_history
-- Stores conversation history with users
CREATE TABLE
    IF NOT EXISTS chat_history (
        mobile_number VARCHAR(20) PRIMARY KEY,
        name VARCHAR(100),
        message_id VARCHAR(100),
        time_stamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        message_type VARCHAR(50),
        INDEX idx_mobile_number (mobile_number),
        INDEX idx_time_stamp (time_stamp)
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- Table: messages
-- Stores all message content
CREATE TABLE
    IF NOT EXISTS messages (
        message_id VARCHAR(100) PRIMARY KEY,
        message TEXT,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        INDEX idx_created_at (created_at)
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- Table: user_details
-- Stores user KYC and application information
CREATE TABLE
    IF NOT EXISTS user_details (
        mobile_id VARCHAR(20) PRIMARY KEY,
        application_id VARCHAR(50),
        name VARCHAR(100),
        pan VARCHAR(10),
        aadhaar VARCHAR(12),
        INDEX idx_application_id (application_id),
        INDEX idx_pan (pan)
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- Table: stage_tracker
-- Tracks user journey through the application process
CREATE TABLE
    IF NOT EXISTS stage_tracker (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        application_id VARCHAR(50),
        mobile_id VARCHAR(20) NOT NULL,
        current_stage VARCHAR(50),
        last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        INDEX idx_mobile_id (mobile_id),
        INDEX idx_application_id (application_id),
        INDEX idx_current_stage (current_stage)
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- Sample queries for monitoring
-- Check all active users
-- SELECT mobile_id, current_stage, last_updated FROM stage_tracker ORDER BY last_updated DESC;
-- Check user conversation history
-- SELECT * FROM chat_history WHERE mobile_number = 'YOUR_MOBILE_NUMBER' ORDER BY time_stamp DESC;
-- Check user details
-- SELECT * FROM user_details WHERE mobile_id = 'YOUR_MOBILE_NUMBER';
-- Count users by stage
-- SELECT current_stage, COUNT(*) as user_count FROM stage_tracker GROUP BY current_stage;
-- Recent applications
-- SELECT u.name, u.application_id, s.current_stage, s.last_updated 
-- FROM user_details u 
-- JOIN stage_tracker s ON u.mobile_id = s.mobile_id 
-- ORDER BY s.last_updated DESC 
-- LIMIT 10;
-- Table: config_properties
-- Generic key-value configuration storage (e.g. WhatsApp tokens)
CREATE TABLE
    IF NOT EXISTS config_properties (
        config_key VARCHAR(100) PRIMARY KEY,
        config_value VARCHAR(1000) NOT NULL,
        description VARCHAR(255)
    ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- Example seed for WhatsApp access token (replace VALUE with real token)
-- INSERT INTO config_properties (config_key, config_value, description)
-- VALUES ('whatsapp.access.token', 'REPLACE_WITH_REAL_TOKEN', 'WhatsApp Business API access token');