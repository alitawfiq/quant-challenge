package io.overledger.springboottemplateservice.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Service;

import io.overledger.springboottemplateservice.dto.TemplateRequest;
import io.overledger.springboottemplateservice.dto.TemplateResponse;
import io.overledger.springboottemplateservice.exceptions.TemplateAlreadyExistExeption;
import io.overledger.springboottemplateservice.exceptions.TemplateException;
import io.overledger.springboottemplateservice.exceptions.TemplateNotFoundException;
import io.overledger.springboottemplateservice.mongodb.TemplateDocument;
import io.overledger.springboottemplateservice.mongodb.TemplateRepository;
import io.overledger.springboottemplateservice.rabbitmq.TemplatePublishGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Slf4j
public class TemplateService {

    TemplateRepository templateRepository;
    TemplatePublishGateway templatePublishGateway;
    
    Map<Integer,String> cache=new HashMap<>();

    public Mono<TemplateResponse> postStuff(TemplateRequest templateRequest) {
        log.info(String.format("Validating request: %s.", templateRequest.toString()));
        if (templateRequest.getTemplateField() == null) {
            throw new TemplateException("The request is missing the required 'templateField' field.");
        }
        if (templateRequest.getTemplateField().equals("How are you?")) {
            return Mono.just(new TemplateResponse("Always peachy!"));
        }

        // Publishes to the Queue. If you run the same service again, with the Channel Handler enabled,
        // you can comment out the 'saveToDatabase' function below as the ChannelHandler will do that instead.
        publishToQueue(templateRequest);

        // Saves to the database. Comment this out if you want to test the Consumer functionality, as the TemplateChannelHandler
        // is used to save to the database.
        saveToDatabase(templateRequest);

        List<TemplateDocument> templateMessages = this.templateRepository
                .findAllByTemplateField(templateRequest.getTemplateField())
                .collectList()
                .block();
        if (templateMessages.size() == 5)
            return Mono.just(new TemplateResponse("42"));

        return Mono.just(new TemplateResponse(templateRequest.getTemplateField()));
    }

    public Mono<TemplateDocument> getStuff(String templatePathVariable) {
        return this.templateRepository
                .findByTemplateField(templatePathVariable)
                .switchIfEmpty(Mono.error(new TemplateNotFoundException("The document was not found.")));
    }

    private void publishToQueue(TemplateRequest templateRequest) {
        log.info("Publishing the request to the queue.");
        
        String templateField = templateRequest.getTemplateField();

        if (cache.containsValue(templateField))
        	throw new TemplateAlreadyExistExeption("Template Already Exists, please try with another template name");
        
        this.templatePublishGateway
                .templatePublishRequest(
                        templateRequest,
                        templateField,
                        System.currentTimeMillis(),
                        templateRequest.getClass().getName()
                );
    }

    public void saveToDatabase(TemplateRequest templateRequest) {
        log.info("Saving the message to the database.");

        String templateField = templateRequest.getTemplateField();
        
        if (cache.containsValue(templateField))
        	throw new TemplateAlreadyExistExeption("Template Already Exists, please try with another template name");
        
        this.templateRepository
                .save(new TemplateDocument(UUID.randomUUID(), templateField))
                .subscribe(result -> log.info(String.valueOf(result)));
        
        Random rnd = new Random();
        
        if(!cache.containsKey(rnd.nextInt()))
        	cache.put(rnd.nextInt(), templateField);

    }
}
