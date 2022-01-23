package br.com.fermino.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		//via extension localhost:8080/person.xml
		/*
		 * configurer.favorParameter(false) .ignoreAcceptHeader(false)
		 * .defaultContentType(MediaType.APPLICATION_JSON)
		 * .mediaType("json",MediaType.APPLICATION_JSON)
		 * .mediaType("xml",MediaType.APPLICATION_XML);
		 */
		
		//via parameter localhost:8080/person?mediaType=xml
		/*
		 * configurer.favorPathExtension(false) .favorParameter(true)
		 * .parameterName("mediaType") .ignoreAcceptHeader(true)
		 * .useRegisteredExtensionsOnly(false)
		 * .defaultContentType(MediaType.APPLICATION_JSON)
		 * .mediaType("json",MediaType.APPLICATION_JSON)
		 * .mediaType("xml",MediaType.APPLICATION_XML);
		 */
		
		//Header Accept: application/xml
		configurer.favorPathExtension(false)
		.favorParameter(false)
		.ignoreAcceptHeader(false)
		.useRegisteredExtensionsOnly(false)
		.defaultContentType(MediaType.APPLICATION_JSON)
		.mediaType("json",MediaType.APPLICATION_JSON)
		.mediaType("xml",MediaType.APPLICATION_XML);
	}
}
