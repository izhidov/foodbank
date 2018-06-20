package com.inzami.fp.config;

import com.inzami.fp.inner.security.UnauthorizedEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@DependsOn("AppBeansConfiguration")
@EnableWebSecurity(debug = false)
@Order(101)
@Profile({"!no-security"})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private org.springframework.security.core.userdetails.UserDetailsService userDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UnauthorizedEntryPoint unauthorizedEntryPoint;
	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder);
	}

//	@Bean
//	public RoleHierarchyImpl roleHierarchy() {
//		RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
//		hierarchy.setHierarchy("ADMIN > USER");
//		return hierarchy;
//	}
//
//	@Bean
//	public SecurityExpressionHandler<FilterInvocation> webExpressionHandler() {
//		DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
//		handler.setRoleHierarchy(roleHierarchy());
//		return handler;
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
//				.sessionManagement()
//					.maximumSessions(2)
//					.expiredUrl("/login.html?expired")
//					.and()
//				.and()
				.csrf()
					.disable()
//				.headers()
//					.frameOptions()
//						.disable()
//				.and()
//					.anonymous()
//				.and()
				.authorizeRequests()
//					.expressionHandler(webExpressionHandler())
					.antMatchers("/view/**").authenticated()
					.antMatchers("/admin/**").hasAuthority("ADMIN")
					.anyRequest()
						.authenticated()
				.and()
				.formLogin()
					.loginPage("/login")
					.permitAll()
					.failureUrl("/login?error")
//				.and()
//					.httpBasic()
//					.authenticationEntryPoint(unauthorizedEntryPoint)
//				.and()
//				.rememberMe()
				.and()
				.logout()
					.invalidateHttpSession(true)
					.clearAuthentication(true)
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
					.logoutSuccessUrl("/login?logout")
					.deleteCookies("JSESSIONID", "CSRF-TOKEN")
					.permitAll()
				.and()
				.exceptionHandling()
					.accessDeniedHandler(accessDeniedHandler)
					.accessDeniedPage("/access-denied")
				.and()
				.requestCache();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
				.antMatchers(HttpMethod.OPTIONS, "/**")  //need for CORS
				.antMatchers("/favicon.ico")
				.antMatchers("/webjars/**")
				.antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**");
	}

//	@Bean
//	public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
//		return new SecurityEvaluationContextExtension();
//	}

}
