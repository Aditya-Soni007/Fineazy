package com.hackathon.lending.bot.service.processor;

import com.hackathon.lending.bot.dto.MessageContext;
import com.hackathon.lending.bot.dto.MessageProcessorRequestDTO;
import com.hackathon.lending.bot.utility.ApplicationStages;
import com.hackathon.lending.bot.utility.ApplicationStages.StageType;
import java.util.Optional;
import org.springframework.util.StringUtils;

public abstract class BaseStageMessageProcessor implements StageMessageProcessor {
    
    private final StageType stageType;
    
    protected BaseStageMessageProcessor(StageType stageType) {
        this.stageType = stageType;
    }
    
    @Override
    public StageType getStageType() {
        return stageType;
    }
    
    protected String buildDefaultResponse(MessageProcessorRequestDTO request, ApplicationStages stage) {
        String user = Optional.ofNullable(request)
                .map(MessageProcessorRequestDTO::getMessageContext)
                .map(MessageContext::getUserName)
                .filter(StringUtils::hasText)
                .orElse("there");
        
        return String.format(
                "Hey %s, we're currently working on the %s stage (%s). We'll update you shortly.",
                user,
                stageType.getDisplayName(),
                stage.name()
        );
    }
}

