package com.inzami.fp.config;

import org.codehaus.groovy.runtime.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.Arrays;

@Configuration
public class ThymeleafConfiguration {

//    @Bean
//    public ClassLoaderTemplateResolver emailTemplateResolver() {
//        ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver();
//        emailTemplateResolver.setPrefix("templates/");
//        emailTemplateResolver.setSuffix(".html");
//        emailTemplateResolver.setTemplateMode("XHTML");
//        emailTemplateResolver.setCharacterEncoding("UTF-8");
//        emailTemplateResolver.setOrder(1);
//        return emailTemplateResolver;
//    }

//    @Autowired
//    private ApplicationContext applicationContext;
//
//    @Bean
//    public ViewResolver htmlViewResolver() {
//        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
//        resolver.setTemplateEngine(templateEngine(htmlTemplateResolver()));
//        resolver.setContentType("text/html");
//        resolver.setCharacterEncoding("UTF-8");
//        resolver.setViewNames(new String[]{"*.html"});
//        return resolver;
//    }
//
//    private TemplateEngine templateEngine(ITemplateResolver templateResolver) {
//        SpringTemplateEngine engine = new SpringTemplateEngine();
//        engine.setTemplateResolver(templateResolver);
//        return engine;
//    }
//
//    private ITemplateResolver htmlTemplateResolver() {
//        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
//        resolver.setApplicationContext(applicationContext);
//        resolver.setPrefix("/WEB-INF/templates/");
//        resolver.setCacheable(false);
//        resolver.setTemplateMode(TemplateMode.HTML);
//        return resolver;
//    }
}
