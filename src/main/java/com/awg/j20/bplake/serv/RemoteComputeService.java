package com.awg.j20.bplake.serv;

import java.io.IOException;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.awg.j20.bplake.config.RemoteComputatorConfig;
import com.awg.j20.bplake.domain.Computation;
import com.awg.j20.bplake.domain.ComputationResult;

@Service
@Profile("comp-remote")
public class RemoteComputeService implements ComputeService {
	private Logger logger = LoggerFactory.getLogger("RemoteComputeService");
	
	@Autowired
	private RemoteComputatorConfig computatorConfig;

	@Override
	public ComputationResult compute(Computation computation) {
		logger.info("Compute remotely for: " + computation);
		logger.info("Computator URL: " + computatorConfig.computatorUrl());
		
		//REST
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new RemoteComputationErrorHandler());
		RequestEntity<Computation> request = RequestEntity
				     			.post(submitionUri())
				     			.accept(MediaType.APPLICATION_JSON)
				     			.body(computation);
		ResponseEntity<ComputationResult> response = restTemplate.exchange(request, ComputationResult.class);
		
		logger.info("RemComp response.Code: [{}], Body: [{}].", response.getStatusCode(), response.getBody());
		
		
		ComputationResult compResult = response.getBody();
		compResult.specifyComputationType("RemoteFromComputator");
		return compResult;
	}
	
	private URI submitionUri() {
		String uriTemplate = computatorConfig.computatorUrl() + "/compute/submit";
		UriComponents uriComponents = UriComponentsBuilder.fromUriString(uriTemplate)  
		        //.queryParam("q", "{q}")  
		        .build();
		return uriComponents.encode().toUri();
	}
	
	public class RemoteComputationErrorHandler implements ResponseErrorHandler {
		  @Override
		  public void handleError(ClientHttpResponse response) throws IOException {
			  logger.error("Caught Remote computation error. Handling it here...");
		  }

		  @Override
		  public boolean hasError(ClientHttpResponse response) throws IOException {
		     if(response.getStatusCode().is5xxServerError()) {
		    	 logger.error("Remote computation error: {}", response.getStatusText());
		    	 return true;
		    	 
		     }
		     return false;
		  }
		}
}
