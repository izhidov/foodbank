package com.inzami.fp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.Date;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

@Configuration
@EnableSwagger2
@Import({springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class})
@Profile({"!no-security", "dev"})
public class SwaggerConfiguration {

    public static final String CONTROLLERS_PATH = "com.inzami.fp.rest.controller";
    private final Logger log = LoggerFactory.getLogger(SwaggerConfiguration.class);

    public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";

    @Value("${fp.swagger.title}")
    private String title = "fp API";
    @Value("${fp.swagger.description}")
    private String description = "fp API documentation";
    @Value("${fp.swagger.version}")
    private String version = "1.0";
    @Value("${fp.swagger.termsOfServiceUrl}")
    private String termsOfServiceUrl;
    @Value("${fp.swagger.contactName}")
    private String contactName;
    @Value("${fp.swagger.contactUrl}")
    private String contactUrl;
    @Value("${fp.swagger.contactEmail}")
    private String contactEmail;
    @Value("${fp.swagger.license}")
    private String license;
    @Value("${fp.swagger.licenseUrl}")
    private String licenseUrl;

    @Bean
    public Docket swaggerSpringfoxDocket() {
        log.debug("Starting Swagger");
        StopWatch watch = new StopWatch();
        watch.start();

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("full-fp-api")
                .apiInfo(apiInfo())
                .forCodeGeneration(true)
                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false)
                .ignoredParameterTypes(Pageable.class)
                .ignoredParameterTypes(java.sql.Date.class)
                .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
                .directModelSubstitute(java.time.LocalDateTime.class, Date.class)
                .select()
                .apis(basePackage(CONTROLLERS_PATH))
                .paths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
                .build()
                .protocols(Collections.singleton("http"));;

        watch.stop();
        log.debug("Started Swagger in {} ms", watch.getTotalTimeMillis());
        return docket;
    }

    private ApiInfo apiInfo() {

        Contact contact = new Contact(
                contactName,
                contactUrl,
                contactEmail);

        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .termsOfServiceUrl(termsOfServiceUrl)
                .contact(contact)
                .license(license)
                .licenseUrl(licenseUrl)
                .version(version)
                .build();
    }

    @Bean
    public SecurityConfiguration securityInfo() {

        String clientId = "internal";
        String clientSecret = "internal";
        String realm = "read";
        String appName = "app_resource_id";
        String apiKeyValue = "123";
        ApiKeyVehicle apiKeyVehicle = ApiKeyVehicle.HEADER;
        String apiKeyName = "apiKeyName";
        String scopeSeparator = "";

        return new SecurityConfiguration(clientId, clientSecret, realm, appName, apiKeyValue, apiKeyVehicle, apiKeyName, scopeSeparator);
    }
}
