package com.web.authentication;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.web.model.User;
import com.web.service.UserService;

public class AuthHandler extends HandlerInterceptorAdapter {

	private static final Logger logger = Logger.getLogger(AuthHandler.class);

	@Autowired
	UserService userService;
	
	/**
	 * Handler to check the auth code for each request
	 * */
	// before the actual handler will be executed
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String authToken = request.getHeader("AuthToken");
		
		logger.info("Client Send Auth Token:" + authToken);
		
		String userName = userService.decryptToken(authToken);
		logger.info("Client Identified as user:" + userName);
		
		User user = userService.getUser(userName);
		if (user != null) {
			logger.info("User found in db:" + user.getUserId());
			boolean validSession = userService.validateSession(user, request, authToken);
			if(validSession){
				logger.info("Session valid for user:" + user.getUsername());
				return true;
			}else{
				logger.error("Session not valid for user:" + user.getUsername());
				return false;
			}
		} else{
			logger.error("Not a valid token:" + authToken);
			PrintWriter writer = response.getWriter();
			writer.print("Invalid Auth Token:"+authToken);
			writer.flush();
			writer.close();
			return false;
		}
	}

	// after the handler is executed
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}
}
