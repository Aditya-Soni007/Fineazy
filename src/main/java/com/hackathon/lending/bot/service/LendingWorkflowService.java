package com.hackathon.lending.bot.service;

import com.hackathon.lending.bot.entity.StageTracker;
import com.hackathon.lending.bot.entity.UserDetails;
import com.hackathon.lending.bot.repository.StageTrackerRepository;
import com.hackathon.lending.bot.repository.UserDetailsRepository;
import com.hackathon.lending.bot.utility.ApplicationStages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class LendingWorkflowService {
    
    private static final Logger logger = LoggerFactory.getLogger(LendingWorkflowService.class);
    
    @Autowired
    private StageTrackerRepository stageTrackerRepository;
    
    @Autowired
    private UserDetailsRepository userDetailsRepository;
    
    /**
     * Process user input based on current stage
     */
    public String processUserInput(String mobileId, String userInput, String currentStage) {
        logger.info("Processing input for stage: {}", currentStage);
        
        switch (currentStage) {
            case ApplicationStages.ONBOARDING:
                return handleOnboarding(mobileId, userInput);
                
            case ApplicationStages.CREATE_APPLICATION:
                return handleCreateApplication(mobileId, userInput);
                
            case ApplicationStages.KYC:
                return handleKYC(mobileId, userInput);
                
            case ApplicationStages.ELIGIBILITY:
                return handleEligibility(mobileId, userInput);
                
            case ApplicationStages.OFFER:
                return handleOffer(mobileId, userInput);
                
            case ApplicationStages.DOCUMENTS_VERIFICATION:
                return handleDocumentsVerification(mobileId, userInput);
                
            case ApplicationStages.DISBURSAL:
                return handleDisbursal(mobileId, userInput);
                
            case ApplicationStages.POST_DISBURSAL:
                return handlePostDisbursal(mobileId, userInput);
                
            default:
                return "Invalid stage. Please contact support.";
        }
    }
    
    /**
     * Handle onboarding stage
     */
    private String handleOnboarding(String mobileId, String userInput) {
        String response = "Welcome to our lending service! 🏦\n\n" +
                "We help you get loans quickly and easily.\n\n" +
                "To get started, please reply with your full name.";
        
        // If user provided a name, move to next stage
        if (userInput != null && !userInput.trim().isEmpty() && userInput.length() > 2) {
            UserDetails userDetails = new UserDetails();
            userDetails.setMobileId(mobileId);
            userDetails.setName(userInput.trim());
            userDetailsRepository.save(userDetails);
            
            updateStage(mobileId, ApplicationStages.CREATE_APPLICATION);
            
            response = String.format("Thank you, %s! ✅\n\n" +
                    "Now let's create your loan application.\n\n" +
                    "Would you like to:\n" +
                    "1. Apply for a Personal Loan\n" +
                    "2. Apply for a Business Loan\n\n" +
                    "Please reply with 1 or 2.", userInput.trim());
        }
        
        return response;
    }
    
    /**
     * Handle create application stage
     */
    private String handleCreateApplication(String mobileId, String userInput) {
        String applicationId = "APP" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        Optional<UserDetails> userDetailsOpt = userDetailsRepository.findById(mobileId);
        if (userDetailsOpt.isPresent()) {
            UserDetails userDetails = userDetailsOpt.get();
            userDetails.setApplicationId(applicationId);
            userDetailsRepository.save(userDetails);
            
            // Update application ID in stage tracker
            Optional<StageTracker> trackerOpt = stageTrackerRepository.findByMobileId(mobileId);
            if (trackerOpt.isPresent()) {
                StageTracker tracker = trackerOpt.get();
                tracker.setApplicationId(applicationId);
                stageTrackerRepository.save(tracker);
            }
        }
        
        updateStage(mobileId, ApplicationStages.KYC);
        
        return String.format("Great! Your application ID is: %s\n\n" +
                "Now let's complete your KYC (Know Your Customer) process.\n\n" +
                "Please provide your PAN number:", applicationId);
    }
    
    /**
     * Handle KYC stage
     */
    private String handleKYC(String mobileId, String userInput) {
        Optional<UserDetails> userDetailsOpt = userDetailsRepository.findById(mobileId);
        
        if (userDetailsOpt.isPresent()) {
            UserDetails userDetails = userDetailsOpt.get();
            
            // Simple validation - check if PAN is empty
            if (userDetails.getPan() == null || userDetails.getPan().isEmpty()) {
                // Save PAN
                userDetails.setPan(userInput.trim().toUpperCase());
                userDetailsRepository.save(userDetails);
                return "Thank you! Now please provide your Aadhaar number:";
            } else if (userDetails.getAadhaar() == null || userDetails.getAadhaar().isEmpty()) {
                // Save Aadhaar
                userDetails.setAadhaar(userInput.trim());
                userDetailsRepository.save(userDetails);
                
                updateStage(mobileId, ApplicationStages.ELIGIBILITY);
                
                return "KYC details saved successfully! ✅\n\n" +
                        "Checking your eligibility... Please wait.";
            }
        }
        
        return "Please provide your PAN number to continue.";
    }
    
    /**
     * Handle eligibility stage
     */
    private String handleEligibility(String mobileId, String userInput) {
        // Mock eligibility check
        updateStage(mobileId, ApplicationStages.OFFER);
        
        return "Great news! You are eligible for a loan! 🎉\n\n" +
                "Based on your profile, we can offer you:\n\n" +
                "💰 Loan Amount: ₹5,00,000\n" +
                "📊 Interest Rate: 12% per annum\n" +
                "⏰ Tenure: Up to 36 months\n\n" +
                "Would you like to proceed with this offer?\n" +
                "Reply with YES or NO.";
    }
    
    /**
     * Handle offer stage
     */
    private String handleOffer(String mobileId, String userInput) {
        if (userInput.trim().equalsIgnoreCase("YES")) {
            updateStage(mobileId, ApplicationStages.DOCUMENTS_VERIFICATION);
            
            return "Excellent! 🎊\n\n" +
                    "Now we need to verify some documents.\n\n" +
                    "Please upload:\n" +
                    "1. Income proof (last 3 months salary slips)\n" +
                    "2. Address proof\n" +
                    "3. Bank statements (last 6 months)\n\n" +
                    "Reply 'DONE' once you have the documents ready.";
        } else if (userInput.trim().equalsIgnoreCase("NO")) {
            return "No problem! If you change your mind, feel free to reach out.\n\n" +
                    "Is there anything else I can help you with?";
        }
        
        return "Please reply with YES to accept the offer or NO to decline.";
    }
    
    /**
     * Handle documents verification stage
     */
    private String handleDocumentsVerification(String mobileId, String userInput) {
        if (userInput.trim().equalsIgnoreCase("DONE")) {
            updateStage(mobileId, ApplicationStages.DISBURSAL);
            
            return "Documents received! 📄✅\n\n" +
                    "Our team will verify your documents within 24 hours.\n" +
                    "Once verified, we'll proceed with the disbursal.\n\n" +
                    "You'll receive a notification once the amount is disbursed to your account.";
        }
        
        return "Please upload all required documents and reply 'DONE' when ready.";
    }
    
    /**
     * Handle disbursal stage
     */
    private String handleDisbursal(String mobileId, String userInput) {
        updateStage(mobileId, ApplicationStages.POST_DISBURSAL);
        
        return "🎉 Congratulations! 🎉\n\n" +
                "Your loan has been disbursed!\n" +
                "The amount will reflect in your account within 2-3 business hours.\n\n" +
                "You can check your repayment schedule and make payments anytime.\n" +
                "Reply with 'STATUS' to check your loan status.";
    }
    
    /**
     * Handle post disbursal stage
     */
    private String handlePostDisbursal(String mobileId, String userInput) {
        if (userInput.trim().equalsIgnoreCase("STATUS")) {
            return "📊 Loan Status:\n\n" +
                    "Loan Amount: ₹5,00,000\n" +
                    "Outstanding: ₹4,85,000\n" +
                    "Next EMI: ₹15,000 (Due: 05-Dec-2024)\n\n" +
                    "Reply 'PAY' to make a payment.";
        }
        
        return "How can I help you today?\n\n" +
                "Reply with:\n" +
                "- STATUS to check loan status\n" +
                "- PAY to make a payment\n" +
                "- HELP for assistance";
    }
    
    /**
     * Update stage for user
     */
    private void updateStage(String mobileId, String newStage) {
        Optional<StageTracker> trackerOpt = stageTrackerRepository.findByMobileId(mobileId);
        if (trackerOpt.isPresent()) {
            StageTracker tracker = trackerOpt.get();
            tracker.setCurrentStage(newStage);
            stageTrackerRepository.save(tracker);
            logger.info("Updated stage for {} to {}", mobileId, newStage);
        }
    }
}

