package com.awg.j20.bplake.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("netclient")
public class RemoteComputatorConfig {
	
	@Value("${computator.url}")
	private String computatorUrl;
	
	public String computatorUrl() {
		return computatorUrl;
	}

}
