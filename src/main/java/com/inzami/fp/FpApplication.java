package com.inzami.fp;

import com.inzami.fp.exception.EntityNotFoundInServiceException;
import com.inzami.fp.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class FpApplication extends SpringBootServletInitializer {

	private static ConfigurationService configurationService;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(FpApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(FpApplication.class);
	}

	@PostConstruct
	public void afterStartup() throws EntityNotFoundInServiceException {
		configurationService.loadConfigurationFromDb();
	}

	@Autowired
	public void setConfigurationService(ConfigurationService configurationService) {
		FpApplication.configurationService = configurationService;
	}
}
