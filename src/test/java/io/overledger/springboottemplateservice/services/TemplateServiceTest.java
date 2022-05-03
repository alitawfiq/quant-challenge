package io.overledger.springboottemplateservice.services;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

import io.overledger.springboottemplateservice.SpringBootTemplateApplication;
import io.overledger.springboottemplateservice.dto.TemplateRequest;
import io.overledger.springboottemplateservice.dto.TemplateResponse;
import io.overledger.springboottemplateservice.mongodb.TemplateDocument;
import io.overledger.springboottemplateservice.mongodb.TemplateRepository;
import reactor.core.publisher.Mono;


@ContextConfiguration(classes = SpringBootTemplateApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TemplateServiceTest {

	static final String TEMPLATE_FIELD_VALUE = "$QNT is the best utility token ever!!";
	static final String URI_PATH = "/templates";

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	private TemplateRepository templateRepository;
	
	@MockBean
	private TemplateService templateService;

	@Test
	void testGetStuff_HappyScenario() throws Exception {

		TemplateDocument response = new TemplateDocument();
		response.setTemplateField(TEMPLATE_FIELD_VALUE);

		Mockito.when(templateService.getStuff(TEMPLATE_FIELD_VALUE))
		.thenReturn(Mono.just(response));

		webTestClient.get()
		.uri(URI_PATH + "/" + TEMPLATE_FIELD_VALUE)
		.accept(MediaType.APPLICATION_JSON)
		.exchange()
		.expectStatus().isOk()
		.expectBody()
		.jsonPath("$.templateField").isEqualTo(response.getTemplateField());

	}
	@Test
	public void testGetStuff_toReturn42AsValue_whenDuplicatingRequest5Times() throws Exception{
		
		TemplateRequest requestBody = new TemplateRequest();
		requestBody.setTemplateField(TEMPLATE_FIELD_VALUE);
		
		TemplateResponse response = new TemplateResponse();
		response.setTemplateField("42");
		
		templateRepository.deleteAll().block();
		templateRepository.save(new TemplateDocument(UUID.randomUUID(),TEMPLATE_FIELD_VALUE)).block();
		templateRepository.save(new TemplateDocument(UUID.randomUUID(),TEMPLATE_FIELD_VALUE)).block();
		templateRepository.save(new TemplateDocument(UUID.randomUUID(),TEMPLATE_FIELD_VALUE)).block();
		templateRepository.save(new TemplateDocument(UUID.randomUUID(),TEMPLATE_FIELD_VALUE)).block();

		Mockito.when(templateService.postStuff(requestBody))
		.thenReturn(Mono.just(response));
		
		webTestClient.post()
		.uri(URI_PATH)
		.contentType(MediaType.APPLICATION_JSON)
		.bodyValue(requestBody)
		.exchange()
		.expectStatus().isOk()
		.expectBody()
		.jsonPath("$.templateField").isEqualTo("42");

	}

	@Test
	void testPostStuff_HappyScenario() throws Exception{

		TemplateRequest requestBody = new TemplateRequest();
		requestBody.setTemplateField(TEMPLATE_FIELD_VALUE);

		TemplateResponse response = new TemplateResponse();
		requestBody.setTemplateField(TEMPLATE_FIELD_VALUE);

		Mockito.when(templateService.postStuff(requestBody))
		.thenReturn(Mono.just(response));

		webTestClient.post()
		.uri(URI_PATH)
		.contentType(MediaType.APPLICATION_JSON)
		.bodyValue(requestBody)
		.exchange()
		.expectStatus().isOk()
		.expectBody()
		.jsonPath("$.templateField").isEqualTo(response.getTemplateField());

	}	
	@Test
	void testPostStuff_HowAreYou() throws Exception{
		TemplateRequest requestBody = new TemplateRequest();
		requestBody.setTemplateField("How are you?");

		TemplateResponse response = new TemplateResponse();
		response.setTemplateField("Always peachy!");

		Mockito.when(templateService.postStuff(requestBody))
		.thenReturn(Mono.just(response));

		webTestClient.post()
		.uri(URI_PATH)
		.contentType(MediaType.APPLICATION_JSON)
		.bodyValue(requestBody)
		.exchange()
		.expectStatus().isOk()
		.expectBody()
		.jsonPath("$.templateField").isEqualTo(response.getTemplateField());

	}
}
