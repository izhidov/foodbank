package com.inzami.fp.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.inzami.fp.inner.jackson.ZonedDateTimeStringSerializer;
import com.inzami.fp.inner.jackson.ZonedDateTimeStringDeserializer;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class JacksonConfiguration {

    @Bean
    public HttpMessageConverters jacksonJsonWithLazySupport() {
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.TEXT_PLAIN);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Hibernate5Module());
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(ZonedDateTime.class, new ZonedDateTimeStringDeserializer());
        javaTimeModule.addSerializer(ZonedDateTime.class, new ZonedDateTimeStringSerializer());
        mapper.registerModule(javaTimeModule);

        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setObjectMapper(mapper);

        mappingJackson2HttpMessageConverter.setPrettyPrint(true);
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
        mappingJackson2HttpMessageConverter.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mappingJackson2HttpMessageConverter.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mappingJackson2HttpMessageConverter.getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return new HttpMessageConverters(mappingJackson2HttpMessageConverter);
    }
}
