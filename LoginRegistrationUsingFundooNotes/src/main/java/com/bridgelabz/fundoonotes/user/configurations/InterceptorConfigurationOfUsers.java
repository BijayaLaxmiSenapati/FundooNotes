package com.bridgelabz.fundoonotes.user.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bridgelabz.fundoonotes.user.intercepters.LoggerInterceptor;

/**
 * @author adminstrato
 *
 */
@Configuration
public class InterceptorConfigurationOfUsers implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoggerInterceptor());
	}

}
