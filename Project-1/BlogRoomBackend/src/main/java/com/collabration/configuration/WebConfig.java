package com.collabration.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//Similar to dispatcher-servlet.xml file configuration
@Configuration     
@EnableWebMvc      //<mvc:annotation-driven> tag in dispatcher-servlet.xml file
@ComponentScan(basePackages="com.collabration")     //<context:componet-scan....>
public class WebConfig extends WebMvcConfigurerAdapter {

	public 	WebConfig() {
		System.out.println("WebConfig Class is instantianted");
	}
	
	@Bean(name="multipartResolver")
	public CommonsMultipartResolver commonsMultipartResolver() {
		
		return new CommonsMultipartResolver();
	}
}
