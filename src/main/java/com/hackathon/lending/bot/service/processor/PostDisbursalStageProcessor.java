package com.hackathon.lending.bot.service.processor;

import com.hackathon.lending.bot.dto.MessageProcessorRequestDTO;
import com.hackathon.lending.bot.utility.ApplicationStages;
import org.springframework.stereotype.Component;

@Component
public class PostDisbursalStageProcessor extends BaseStageMessageProcessor {
    
    public PostDisbursalStageProcessor() {
        super(ApplicationStages.StageType.POST_DISBURSAL);
    }
    
    @Override
    public String process(MessageProcessorRequestDTO request, ApplicationStages stage) {
        return buildDefaultResponse(request, stage);
    }
}

