package mcp.http;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = TracingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CloudTraceApplicationTest {

	private RestTemplate restTemplate = new RestTemplate();


	public void frontendShouldInvokeBackend() throws Exception {
		String clientId = "android";
		RequestEntity<Void> requestEntity = RequestEntity.get(URI.create("http://localhost:8080/frontend"))
				.header("client-id", clientId)
				.build();
		ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
		String msg = responseEntity.getBody();
		Assertions.assertThat(msg).isNotNull();
		Assertions.assertThat(msg).contains("Hello, " + clientId);
	}
}