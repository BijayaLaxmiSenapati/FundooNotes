package com.bridgelabz.fundoonotes.user.intercepters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class LoggerInterceptor extends HandlerInterceptorAdapter{

	private static final Logger logger = LoggerFactory.getLogger(LoggerInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object object, Exception arg3)
			throws Exception {
		logger.info("Request for"+request.getRequestURI()+" is complete");
		/*log.info("[preHandle][" + request + "]" + "[" + request.getMethod()
	      + "]" + request.getRequestURI() + getParameters(request));*/
	      
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object object, ModelAndView model)
			throws Exception {
		logger.info("Request for "+request.getRequestURI()+" after execution");
		//log.info("[postHandle][" + request + "]");
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object object) throws Exception {
		System.out.println("before url "+request.getRequestURI());
		logger.info("Request before" +request.getRequestURI());
		// log.info("[afterCompletion][" + request + "][exception: " + ex + "]");
		return true;
	}
	
	
	
}


