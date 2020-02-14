package com.awg.j20.bplake.serv;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withNoContent;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;

import com.awg.j20.bplake.config.RemoteComputatorConfig;
import com.awg.j20.bplake.domain.AlgebraOperatorEnum;
import com.awg.j20.bplake.domain.Computation;
import com.awg.j20.bplake.domain.ComputationResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@RestClientTest(components = RemoteComputeService.class)
@ActiveProfiles(profiles = {"comp-remote", "netclient"})
@AutoConfigureWebClient(registerRestTemplate=true)
public class RemoteComputeServiceTest {
	@MockBean
	private RemoteComputatorConfig mockConfig;
    @Autowired
    private MockRestServiceServer mockRestServiceServer;
	@Autowired
	private RemoteComputeService computeSrvUnderTest;
	@Autowired
	private ObjectMapper objectMapper;
	
	@BeforeTestClass
	public void setup() {
		when(mockConfig.computatorUrl()).thenReturn("http://localhost:9001");
	}

	@Test
	public void whenCompute_thenClientMakesCorrectCall() throws JsonProcessingException {
		Computation comp = new Computation(AlgebraOperatorEnum.MULT, 3, 8);
		ComputationResult expectedCompResult = ComputationResult.forComputation(comp)
													.withResult(24)
													.withComputationType("IntegrationTest");
		
		String serializedResponse = objectMapper.writeValueAsString(expectedCompResult);
		
		this.mockRestServiceServer
				.expect(requestTo(mockConfig.computatorUrl() + "/compute/submit"))
        		.andRespond(withSuccess(serializedResponse, MediaType.APPLICATION_JSON));
		
		//when:
		ComputationResult actual = computeSrvUnderTest.compute(comp);

		assertThat(actual).isNotNull();
		assertThat(actual).extracting("result").isEqualTo(24);
		//I wanna make sure service rewrite the type to it's own regardless response:
		assertThat(actual).extracting("computationType").isEqualTo("RemoteFromComputator");
	}
	
	@Test
	public void whenComputeError_handleException() throws JsonProcessingException {
		Computation comp = new Computation(AlgebraOperatorEnum.MULT, 3, 8);		
		this.mockRestServiceServer
				.expect(requestTo(mockConfig.computatorUrl() + "/compute/submit"))
        		.andRespond(withServerError());
		
		//when:
		RemoteComputationUnavailableException exception = 
				assertThrows(RemoteComputationUnavailableException.class, () -> {
						computeSrvUnderTest.compute(comp);
		 });
	
		assertThat(exception).isNotNull();
		assertThat(exception.getRemoteCallError()).contains("not 200");
	}
	
	@Test
	public void whenComputeReplyNoContent_handleException() throws JsonProcessingException {
		Computation comp = new Computation(AlgebraOperatorEnum.MULT, 3, 8);		
		this.mockRestServiceServer
				.expect(requestTo(mockConfig.computatorUrl() + "/compute/submit"))
        		.andRespond(withNoContent());
		
		//when:
		RemoteComputationUnavailableException exception = 
				assertThrows(RemoteComputationUnavailableException.class, () -> {
						computeSrvUnderTest.compute(comp);
		 });
	
		assertThat(exception).isNotNull();
		assertThat(exception.getRemoteCallError()).contains("empty body");
	}
}
