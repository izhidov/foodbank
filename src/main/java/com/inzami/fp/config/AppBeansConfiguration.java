package com.inzami.fp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.inzami.fp.inner.jackson.ZonedDateTimeStringDeserializer;
import com.inzami.fp.inner.jackson.ZonedDateTimeStringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.ZonedDateTime;

@Configuration("AppBeansConfiguration")
public class AppBeansConfiguration {

    @Bean
    @Profile({"!prod"})
    public PasswordEncoder passwordEncoderNo() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    @Profile({"prod"})
    public PasswordEncoder passwordEncoderBcrypt() {
        return new BCryptPasswordEncoder();
    }
}
