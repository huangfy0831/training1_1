package com.training.handle;

import java.io.IOException;
import java.util.List;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;

public class CustomResponseErrorHandler implements ResponseErrorHandler {

    private ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return errorHandler.hasError(response);
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		// TODO 好きなエラー情報を設定できる
        List<String> customHeader = response.getHeaders().get("x-app-err-id");

        String svcErrorMessageID = "defaultHeader";
        if (customHeader != null) {
            svcErrorMessageID = customHeader.get(0);                
        }

        try {
            errorHandler.handleError(response);

        } catch (RestClientException scx) {
	
            throw new RestClientException(scx.getMessage() + svcErrorMessageID);
        }
	}

}
