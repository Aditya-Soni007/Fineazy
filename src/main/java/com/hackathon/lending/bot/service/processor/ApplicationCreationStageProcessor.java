package com.hackathon.lending.bot.service.processor;

import com.hackathon.lending.bot.dto.MessageProcessorRequestDTO;
import com.hackathon.lending.bot.utility.ApplicationStages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ApplicationCreationStageProcessor extends BaseStageMessageProcessor {

    private static final Logger log = LoggerFactory.getLogger(KycStageProcessor.class);

    public ApplicationCreationStageProcessor() {
        super(ApplicationStages.StageType.APPLICATION_CREATION);
    }
    
    @Override
    public String process(MessageProcessorRequestDTO request, ApplicationStages stage) {

        try{
            return processWithWorkflow(request,stage);
        }
        catch(Exception ex) {
            log.error("Exception occurred , sending default response : ",ex);
            return buildDefaultResponse(request, stage);
        }
    }
}

