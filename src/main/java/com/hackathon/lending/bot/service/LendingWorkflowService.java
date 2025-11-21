package com.hackathon.lending.bot.service;

import com.hackathon.lending.bot.entity.UserDetails;
import com.hackathon.lending.bot.repository.UserDetailsRepository;
import com.hackathon.lending.bot.utility.ApplicationStages;
import com.hackathon.lending.bot.utility.ApplicationStages.StageType;
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
    private UserDetailsRepository userDetailsRepository;
    
    /**
     * Process user input based on current stage
     */
    public String processUserInput(String mobileId, String userInput, String currentStageValue) {
        ApplicationStages currentStage = ApplicationStages.fromValue(currentStageValue);
        logger.info("Processing input for stage: {}", currentStage.name());
        
        StageType stageType = currentStage.getStageType();
        switch (stageType) {
            case ONBOARDING:
                return handleOnboarding(mobileId, userInput);
            case APPLICATION_CREATION:
                return handleCreateApplication(mobileId, userInput);
            case APPLICATION_UPDATE:
                return handleApplicationUpdate(mobileId, userInput);
            case KYC:
                return handleKYC(mobileId, userInput);
            case ELIGIBILITY:
                return handleEligibility(mobileId, userInput);
            case OFFER:
            case OFFER_ACCEPTANCE:
                return handleOffer(mobileId, userInput);
            case DOCUMENTS_VERIFICATION:
                return handleDocumentsVerification(mobileId, userInput);
            case DOCUMENT_SIGNING:
                return handleDocumentSigning(mobileId, userInput);
            case DISBURSAL:
                return handleDisbursal(mobileId, userInput);
            case POST_DISBURSAL:
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
            UserDetails userDetails = userDetailsRepository.findById(mobileId)
                    .orElseGet(() -> {
                        UserDetails newUser = new UserDetails();
                        newUser.setMobileId(mobileId);
                        newUser.setCurrentStage(ApplicationStages.defaultStage().name());
                        return newUser;
                    });
            userDetails.setName(userInput.trim());
            userDetailsRepository.save(userDetails);
            
            updateStage(mobileId, ApplicationStages.APPLICATION_CREATION_IN_PROGRESS);
            
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
        }
        
        updateStage(mobileId, ApplicationStages.KYC_IN_PROGRESS);
        
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
                
                updateStage(mobileId, ApplicationStages.ELIGIBILITY_IN_PROGRESS);
                
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
        updateStage(mobileId, ApplicationStages.OFFER_ACCEPTANCE_IN_PROGRESS);
        
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
            updateStage(mobileId, ApplicationStages.DOCUMENTS_VERIFICATION_IN_PROGRESS);
            
            return "Excellent! 🎊\n\n" +
                    "Now we need to verify some documents.\n\n" +
                    "Please upload:\n" +
                    "1. Income proof (last 3 months salary slips)\n" +
                    "2. Address proof\n" +
                    "3. Bank statements (last 6 months)\n\n" +
                    "Reply 'DONE' once you have the documents ready.";
        } else if (userInput.trim().equalsIgnoreCase("NO")) {
            updateStage(mobileId, ApplicationStages.OFFER_DECLINED);
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
            updateStage(mobileId, ApplicationStages.DISBURSAL_IN_PROGRESS);
            
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
        updateStage(mobileId, ApplicationStages.POST_DISBURSAL_IN_PROGRESS);
        
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
    private String handleApplicationUpdate(String mobileId, String userInput) {
        return "Application update journey is currently in progress. Please hold on while we complete this step.";
    }
    
    private String handleDocumentSigning(String mobileId, String userInput) {
        return "Document signing is in progress. We will notify you once the documents are signed.";
    }
    
    private void updateStage(String mobileId, ApplicationStages newStage) {
        Optional<UserDetails> userDetailsOpt = userDetailsRepository.findById(mobileId);
        if (userDetailsOpt.isPresent()) {
            UserDetails userDetails = userDetailsOpt.get();
            userDetails.setCurrentStage(newStage.name());
            userDetailsRepository.save(userDetails);
            logger.info("Updated stage for {} to {}", mobileId, newStage);
        } else {
            logger.warn("Unable to update stage for {} because user details do not exist", mobileId);
        }
    }
}

