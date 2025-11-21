package com.hackathon.lending.bot.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stage_tracker")
public class StageTracker {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "application_id", length = 50)
    private String applicationId;
    
    @Column(name = "mobile_id", nullable = false, length = 20)
    private String mobileId;
    
    @Column(name = "current_stage", length = 50)
    private String currentStage;
    
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    
    // Constructors
    public StageTracker() {
    }
    
    public StageTracker(String applicationId, String mobileId, String currentStage) {
        this.applicationId = applicationId;
        this.mobileId = mobileId;
        this.currentStage = currentStage;
        this.lastUpdated = LocalDateTime.now();
    }
    
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        lastUpdated = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getApplicationId() {
        return applicationId;
    }
    
    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }
    
    public String getMobileId() {
        return mobileId;
    }
    
    public void setMobileId(String mobileId) {
        this.mobileId = mobileId;
    }
    
    public String getCurrentStage() {
        return currentStage;
    }
    
    public void setCurrentStage(String currentStage) {
        this.currentStage = currentStage;
    }
    
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }
    
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}

