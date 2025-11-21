package com.hackathon.lending.bot.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_details")
public class UserDetails {
    
    @Id
    @Column(name = "mobile_id", nullable = false, length = 20)
    private String mobileId;
    
    @Column(name = "application_id", length = 50)
    private String applicationId;
    
    @Column(name = "name", length = 100)
    private String name;
    
    @Column(name = "pan", length = 10)
    private String pan;
    
    @Column(name = "aadhaar", length = 12)
    private String aadhaar;
    
    @Column(name = "bank_account_number", length = 20)
    private String bankAccountNumber;
    
    @Column(name = "current_stage", length = 50)
    private String currentStage;
    
    // Constructors
    public UserDetails() {
    }
    
    public UserDetails(String mobileId, String applicationId, String name, String pan, String aadhaar) {
        this(mobileId, applicationId, name, pan, aadhaar, null);
    }
    
    public UserDetails(String mobileId, String applicationId, String name, String pan, String aadhaar, String currentStage) {
        this.mobileId = mobileId;
        this.applicationId = applicationId;
        this.name = name;
        this.pan = pan;
        this.aadhaar = aadhaar;
        this.currentStage = currentStage;
    }
    
    // Getters and Setters
    public String getMobileId() {
        return mobileId;
    }
    
    public void setMobileId(String mobileId) {
        this.mobileId = mobileId;
    }
    
    public String getApplicationId() {
        return applicationId;
    }
    
    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPan() {
        return pan;
    }
    
    public void setPan(String pan) {
        this.pan = pan;
    }
    
    public String getAadhaar() {
        return aadhaar;
    }
    
    public void setAadhaar(String aadhaar) {
        this.aadhaar = aadhaar;
    }
    
    public String getBankAccountNumber() {
        return bankAccountNumber;
    }
    
    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }
    
    public String getCurrentStage() {
        return currentStage;
    }
    
    public void setCurrentStage(String currentStage) {
        this.currentStage = currentStage;
    }
}

