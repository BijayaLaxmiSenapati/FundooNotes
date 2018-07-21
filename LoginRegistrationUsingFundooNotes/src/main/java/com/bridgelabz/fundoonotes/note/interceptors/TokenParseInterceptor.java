package com.bridgelabz.fundoonotes.note.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.bridgelabz.fundoonotes.user.security.TokenProvider;

public class TokenParseInterceptor implements HandlerInterceptor {
	
	@Autowired
	private TokenProvider tokenProvider;

	private static final Logger LOGGER=LoggerFactory.getLogger(TokenParseInterceptor.class);
	
	/*@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		LOGGER.info("Before handling the request with request url: " + request.getRequestURI());
		String token=request.getHeader("token");
		String userId=tokenProvider.parseToken(token);
		request.setAttribute("userId", userId);
		return true;
	}*/

}
