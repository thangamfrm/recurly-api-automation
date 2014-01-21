package com.thangamfrm.recurly.rest;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;

public class RecurlyResponseErrorHandler implements ResponseErrorHandler {

	private ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();

	public void handleError(ClientHttpResponse response) throws IOException {

		StringWriter writer = new StringWriter();
		IOUtils.copy(response.getBody(), writer);

		List<String> customHeader = response.getHeaders().get("x-app-err-id");

		String svcErrorMessageID = "";
		if (customHeader != null) {
			svcErrorMessageID = customHeader.get(0);
		}

		try {
			errorHandler.handleError(response);
		} catch (RestClientException scx) {
			throw new RuntimeException(scx.getMessage() + svcErrorMessageID + writer.toString(), scx);
		}
	}

	public boolean hasError(ClientHttpResponse response) throws IOException {
		return errorHandler.hasError(response);
	}

}
