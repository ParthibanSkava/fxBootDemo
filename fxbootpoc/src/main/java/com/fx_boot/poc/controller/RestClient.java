package com.fx_boot.poc.controller;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RestClient {

	public Logger logger = LoggerFactory.getLogger(RestClient.class);

	public static final String USER_AGENT = "Mozilla/5.0";

	private RestTemplate restTemplate = new RestTemplate();

	HttpHeaders headers;

	public RestClient() {
		headers = new HttpHeaders();
		headers.set("User-Agent", USER_AGENT);
		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		headers.add(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8.name());
	}

	@PostConstruct
	public void initConfig() {
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
	}

	@SuppressWarnings("unchecked")
	public <T> Object postForObjects(final String restURLPath, final Object requestBody,
			ParameterizedTypeReference<T> responseType, Object... uriVariables) {
		ResponseEntity<Object> eccpResponse = null;
		try {
			eccpResponse = (ResponseEntity<Object>) restTemplate.exchange(restURLPath, HttpMethod.POST,
					getHttpEntity(requestBody), responseType, uriVariables);
		} catch (RestClientException exception) {
			logger.error(exception.getMessage());
		}

		if (null == eccpResponse) {
			System.out.println("eccpResponse : " + eccpResponse);
			return eccpResponse;
		}
			

		return eccpResponse.getBody();
	}

	public Object putForObject(final String restURLPath, final Object requestBody, Object... uriVariables) {
		ResponseEntity<Object> eccpResponse = null;
		try {
			eccpResponse = restTemplate.exchange(restURLPath, HttpMethod.PUT, getHttpEntity(requestBody), Object.class,
					uriVariables);
		} catch (RestClientException exception) {
			logger.error(exception.getMessage());
		}
		return eccpResponse.getBody();
	}

	public Object getForObject(final String restURLPath, final Object requestBody, Object... uriVariables) {
		ResponseEntity<Object> eccpResponse = null;
		try {
			eccpResponse = restTemplate.exchange(restURLPath, HttpMethod.GET, getHttpEntity(requestBody), Object.class,
					uriVariables);
		} catch (RestClientException exception) {
			logger.error(exception.getMessage());
			return null;
		}
		return eccpResponse.getBody();
	}

	@SuppressWarnings("unchecked")
	public <T> Object getForObjects(final String restURLPath, ParameterizedTypeReference<T> responseType,
			Object... uriVariables) {
		ResponseEntity<Object> eccpResponse = null;
		try {
			eccpResponse = (ResponseEntity<Object>) restTemplate.exchange(restURLPath, HttpMethod.GET,
					new HttpEntity<Object>(headers), responseType, uriVariables);
		} catch (RestClientException exception) {
			logger.error(exception.getMessage());
		}
		return eccpResponse.getBody();
	}

	public String getForString(final String restURLPath, final Object requestBody, Object... uriVariables) {
		ResponseEntity<String> eccpResponse = null;
		try {
			eccpResponse = restTemplate.exchange(restURLPath, HttpMethod.GET, getHttpEntity(requestBody), String.class,
					uriVariables);
		} catch (RestClientException exception) {
			logger.error(exception.getMessage());
		}
		return eccpResponse.getBody();
	}

	@SuppressWarnings("unchecked")
	public <T> Object getForObjects(final String restURLPath, Map<String, String> httpHeaderValues,
			ParameterizedTypeReference<T> responseType, Object... uriVariables) {
		ResponseEntity<Object> eccpResponse = null;
		try {
			eccpResponse = (ResponseEntity<Object>) restTemplate.exchange(restURLPath, HttpMethod.GET,
					new HttpEntity<Object>(getHttpHeadersWithValues(httpHeaderValues)), responseType, uriVariables);
		} catch (RestClientException exception) {
			logger.error(exception.getMessage());
		}
		return eccpResponse.getBody();
	}

	private HttpHeaders getHttpHeadersWithValues(final Map<String, String> httpHeaderValues) {
		HttpHeaders httpHeaders = new HttpHeaders();
		if (isHttpHeadersValuesNotEmpty(httpHeaderValues)) {
			httpHeaders.setAll(httpHeaderValues);
		}
		return httpHeaders;
	}

	private HttpEntity<Object> getHttpEntity(Object requestBody) {
		return new HttpEntity<Object>(requestBody, headers);
	}

	private boolean isHttpHeadersValuesNotEmpty(Map<String, String> httpHeaderValues) {
		return !httpHeaderValues.isEmpty();
	}
}
