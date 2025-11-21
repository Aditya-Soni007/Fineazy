package com.hackathon.lending.bot.service.processor;

import com.hackathon.lending.bot.dto.MessageProcessorRequestDTO;
import com.hackathon.lending.bot.utility.ApplicationStages;
import org.springframework.stereotype.Component;

@Component
public class EligibilityStageProcessor extends BaseStageMessageProcessor {
    
    public EligibilityStageProcessor() {
        super(ApplicationStages.StageType.ELIGIBILITY);
    }
    
    @Override
    public String process(MessageProcessorRequestDTO request, ApplicationStages stage) {
        return buildDefaultResponse(request, stage);
    }
}

