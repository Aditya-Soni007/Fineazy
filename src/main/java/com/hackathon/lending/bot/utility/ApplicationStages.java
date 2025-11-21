package com.hackathon.lending.bot.utility;

import java.util.Arrays;
import java.util.Optional;

/**
 * Enum representing all the stages a user can be in throughout the lending
 * lifecycle.
 * Each stage also indicates if it is an "in progress" milestone so that we can
 * branch
 * the conversational logic based on the current state.
 */
public enum ApplicationStages {

    ONBOARDING_IN_PROGRESS(StageType.ONBOARDING, true),
    ONBOARDING_COMPLETED(StageType.ONBOARDING, false),

    APPLICATION_CREATION_IN_PROGRESS(StageType.APPLICATION_CREATION, true),
    APPLICATION_CREATED(StageType.APPLICATION_CREATION, false),

    APPLICATION_UPDATE_IN_PROGRESS(StageType.APPLICATION_UPDATE, true),
    APPLICATION_DETAILS_UPDATED(StageType.APPLICATION_UPDATE, false),

    KYC_IN_PROGRESS(StageType.KYC, true),
    KYC_SUCCESSFUL(StageType.KYC, false),
    KYC_FAILURE(StageType.KYC, false),

    ELIGIBILITY_IN_PROGRESS(StageType.ELIGIBILITY, true),
    ELIGIBILITY_APPROVED(StageType.ELIGIBILITY, false),
    ELIGIBILITY_DECLINED(StageType.ELIGIBILITY, false),

    OFFER_CREATION_IN_PROGRESS(StageType.OFFER, true),
    OFFER_CREATED(StageType.OFFER, false),

    OFFER_ACCEPTANCE_IN_PROGRESS(StageType.OFFER_ACCEPTANCE, true),
    OFFER_ACCEPTED(StageType.OFFER_ACCEPTANCE, false),
    OFFER_DECLINED(StageType.OFFER_ACCEPTANCE, false),

    DOCUMENTS_VERIFICATION_IN_PROGRESS(StageType.DOCUMENTS_VERIFICATION, true),
    DOCUMENTS_CREATED(StageType.DOCUMENTS_VERIFICATION, false),
    DOCUMENTS_VERIFICATION_FAILED(StageType.DOCUMENTS_VERIFICATION, false),

    DOCUMENT_SIGNING_IN_PROGRESS(StageType.DOCUMENT_SIGNING, true),
    DOCUMENTS_SIGNED(StageType.DOCUMENT_SIGNING, false),

    DISBURSAL_IN_PROGRESS(StageType.DISBURSAL, true),
    LOAN_DISBURSED(StageType.DISBURSAL, false),

    POST_DISBURSAL_IN_PROGRESS(StageType.POST_DISBURSAL, true),
    REPAYMENT_INFO_READY(StageType.POST_DISBURSAL, false),
    POST_DISBURSAL_COMPLETED(StageType.POST_DISBURSAL, false);

    private final StageType stageType;
    private final boolean inProgress;

    ApplicationStages(StageType stageType, boolean inProgress) {
        this.stageType = stageType;
        this.inProgress = inProgress;
    }

    public StageType getStageType() {
        return stageType;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public static ApplicationStages defaultStage() {
        return ONBOARDING_IN_PROGRESS;
    }

    public static ApplicationStages fromValue(String value) {
        if (value == null || value.isBlank()) {
            return defaultStage();
        }
        Optional<ApplicationStages> stage = Arrays.stream(values())
                .filter(entry -> entry.name().equalsIgnoreCase(value.trim()))
                .findFirst();
        return stage.orElse(defaultStage());
    }

    public static ApplicationStages nextStage(ApplicationStages currentStage) {
        ApplicationStages resolved = currentStage != null ? currentStage : defaultStage();
        return switch (resolved) {
            case ONBOARDING_IN_PROGRESS -> APPLICATION_CREATION_IN_PROGRESS;
            case APPLICATION_CREATION_IN_PROGRESS -> KYC_IN_PROGRESS;
            case APPLICATION_UPDATE_IN_PROGRESS -> APPLICATION_DETAILS_UPDATED;
            case KYC_IN_PROGRESS -> ELIGIBILITY_IN_PROGRESS;
            case ELIGIBILITY_IN_PROGRESS -> OFFER_ACCEPTANCE_IN_PROGRESS;
            case OFFER_ACCEPTANCE_IN_PROGRESS -> DOCUMENTS_VERIFICATION_IN_PROGRESS;
            case DOCUMENTS_VERIFICATION_IN_PROGRESS -> DOCUMENT_SIGNING_IN_PROGRESS;
            case DOCUMENT_SIGNING_IN_PROGRESS -> DISBURSAL_IN_PROGRESS;
            case DISBURSAL_IN_PROGRESS -> POST_DISBURSAL_IN_PROGRESS;
            case POST_DISBURSAL_IN_PROGRESS -> POST_DISBURSAL_COMPLETED;
            default -> resolved;
        };
    }

    public enum StageType {
        ONBOARDING("Onboarding"),
        APPLICATION_CREATION("Application Creation"),
        APPLICATION_UPDATE("Application Update"),
        KYC("KYC"),
        ELIGIBILITY("Eligibility"),
        OFFER("Offer Generation"),
        OFFER_ACCEPTANCE("Offer Acceptance"),
        DOCUMENTS_VERIFICATION("Documents Verification"),
        DOCUMENT_SIGNING("Document Signing"),
        DISBURSAL("Disbursal"),
        POST_DISBURSAL("Post Disbursal"),
        UNKNOWN("Unknown");

        private final String displayName;

        StageType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
