package com.hackathon.lending.bot.service.processor;

import com.hackathon.lending.bot.dto.MessageProcessorRequestDTO;
import com.hackathon.lending.bot.utility.ApplicationStages;
import org.springframework.stereotype.Component;

@Component
public class ApplicationCreationStageProcessor extends BaseStageMessageProcessor {
    
    public ApplicationCreationStageProcessor() {
        super(ApplicationStages.StageType.APPLICATION_CREATION);
    }
    
    @Override
    public String process(MessageProcessorRequestDTO request, ApplicationStages stage) {
        return buildDefaultResponse(request, stage);
    }
}

