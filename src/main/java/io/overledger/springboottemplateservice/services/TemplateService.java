package io.overledger.springboottemplateservice.services;

import io.overledger.springboottemplateservice.dto.TemplateRequest;
import io.overledger.springboottemplateservice.dto.TemplateResponse;
import io.overledger.springboottemplateservice.mongodb.TemplateDocument;
import reactor.core.publisher.Mono;

public interface TemplateService {

	Mono<TemplateResponse> postStuff(TemplateRequest templateRequest) ;
    Mono<TemplateDocument> getStuff(String templatePathVariable) ;

}
