package com.bridgelabz.fundoonotes.note.configurations;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bridgelabz.fundoonotes.note.interceptors.TokenParseInterceptor;

public class InterceptorConfigurationOfNotes implements WebMvcConfigurer {
	
	/*@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(new TokenParseInterceptor()).addPathPatterns("/notes/**");
		//have doubt for **
	}*/

}
