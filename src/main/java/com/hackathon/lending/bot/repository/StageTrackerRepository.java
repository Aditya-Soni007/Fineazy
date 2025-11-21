package com.hackathon.lending.bot.repository;

import com.hackathon.lending.bot.entity.StageTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StageTrackerRepository extends JpaRepository<StageTracker, Long> {
    
    Optional<StageTracker> findByMobileId(String mobileId);
    
    Optional<StageTracker> findByApplicationId(String applicationId);
}

