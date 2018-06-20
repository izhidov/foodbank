package com.inzami.fp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Order(1)
@Configuration
@Profile({"!no-security", "dev"})
public class SwaggerWebSecurityConfig extends WebSecurityConfigurerAdapter {
  private static final String[] SWAGGER_URLS = {"/swagger-resources/**", "/v2/**", "/webjars/**", "/swagger-ui.html"};

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager(Collections.emptySet());
    manager.createUser(new User("developer", "hell0w0rld", Collections.emptyList()));

    http.csrf().disable()
      .requestMatcher(new OrRequestMatcher(Stream.of(SWAGGER_URLS).map(AntPathRequestMatcher::new).collect(Collectors.toList())))
      .userDetailsService(manager)
      .authorizeRequests().anyRequest().authenticated()
      .and().httpBasic();
  }
}
