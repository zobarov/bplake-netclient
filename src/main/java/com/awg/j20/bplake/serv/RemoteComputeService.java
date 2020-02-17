package com.awg.j20.bplake.serv;

import java.io.IOException;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.awg.j20.bplake.config.RemoteComputatorConfig;
import com.awg.j20.bplake.domain.Computation;
import com.awg.j20.bplake.domain.ComputationResult;

/**
 * Computation service that performs remote REST call to obtain results.
 */
@Service
@Profile({"comp-remote", "netclient"})
public class RemoteComputeService implements ComputeService {
	private Logger logger = LoggerFactory.getLogger("RemoteComputeService");
	
	@Autowired
	private RemoteComputatorConfig computatorConfig;
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public ComputationResult compute(Computation computation) {
		logger.info("Compute remotely for: " + computation);
		logger.info("Computator submition URL: " + submitionUri());

		restTemplate.setErrorHandler(new RemoteComputationErrorHandler());
		RequestEntity<Computation> request = RequestEntity
				     			.post(submitionUri())
				     			.accept(MediaType.APPLICATION_JSON)
				     			.body(computation);
		try {
			ResponseEntity<ComputationResult> response = restTemplate.exchange(request, ComputationResult.class);
			
			if(response.getStatusCode().is2xxSuccessful()) {
				logger.info("RemComp response.Code: [{}], Body: [{}].", response.getStatusCode(), response.getBody());

				ComputationResult compResult = response.getBody();
				if(compResult == null) {
					String msg = "Remote computation replied with empty body";
					logger.error(msg);
					throw new RemoteComputationUnavailableException(msg);
				}
				compResult.withComputationType("RemoteFromComputator");
				return compResult;
			}
			String msg = "Remote computation status is not 200";
			logger.error(msg);
			throw new RemoteComputationUnavailableException(msg);			
		} catch (RestClientException restEx) {
			logger.error("Remote computation error: " + restEx.getMessage());
			throw new RemoteComputationUnavailableException(restEx.getMessage());
		}
	}
	
	private URI submitionUri() {
		String uriTemplate = computatorConfig.computatorUrl() + "/compute/submit";
		UriComponents uriComponents = UriComponentsBuilder
										.fromUriString(uriTemplate)  
										.build();
		return uriComponents.encode().toUri();
	}
	
	/**
	 * Handles remote calls errors
	 */
	public class RemoteComputationErrorHandler implements ResponseErrorHandler {
		  @Override
		  public void handleError(ClientHttpResponse response) throws IOException {
			  logger.error("Caught Remote computation error.");
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
