package com.thangamfrm.recurly.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.client.RestTemplate;

public class BaseClient {

	@Value("#{recurlyAPIProperties['recurly.api.key']}")
	protected String apiKey;

	@Autowired
    protected RestTemplate restTemplate;

	protected String getAuthorizationKey() {
		return "Basic " + new String(Base64.encode(new String(apiKey).getBytes()));
	}

}
