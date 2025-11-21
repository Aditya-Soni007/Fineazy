package com.hackathon.lending.bot.repository;

import com.hackathon.lending.bot.entity.ChatHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatHistoryRepository extends JpaRepository<ChatHistory, String> {
    
    List<ChatHistory> findByMobileNumberOrderByTimeStampDesc(String mobileNumber);
    
    ChatHistory findTopByMobileNumberOrderByTimeStampDesc(String mobileNumber);
}

