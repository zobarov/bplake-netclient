package com.awg.j20.bplake.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

/**
 * Basic configuration for remote server.
 */
@Configuration
@Profile("netclient")
public class RemoteComputatorConfig {
	
	@Value("${computator.url}")
	private String computatorUrl;
	
	public String computatorUrl() {
		return computatorUrl;
	}
	
	@Bean
	public RestTemplate resttemplate() {
		return new RestTemplateBuilder().build();
	}

}
