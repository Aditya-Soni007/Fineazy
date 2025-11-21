package com.hackathon.lending.bot.service.processor;

import com.hackathon.lending.bot.dto.MessageProcessorRequestDTO;
import com.hackathon.lending.bot.utility.ApplicationStages;
import com.hackathon.lending.bot.utility.ApplicationStages.StageType;

public interface StageMessageProcessor {
    
    StageType getStageType();
    
    String process(MessageProcessorRequestDTO request, ApplicationStages stage);
}

