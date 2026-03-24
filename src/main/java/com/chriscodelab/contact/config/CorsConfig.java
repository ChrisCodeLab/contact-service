package com.chriscodelab.contact.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.*;
import org.springframework.web.filter.CorsFilter;


@Configuration
public class CorsConfig {
	
	@Bean
	public CorsFilter corsFilter() {
		
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(List.of(
				"https://chriscodelab.com",
				"https://chriscodelab-site.pages.dev",
			    "http://localhost:5173"
			));
		config.setAllowedHeaders(List.of("*"));
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowCredentials(false);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		
		return new CorsFilter(source);
		
		
		
	}

	
}
