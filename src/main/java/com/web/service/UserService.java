package com.web.service;

import javax.servlet.http.HttpServletRequest;

import com.web.model.User;


public interface UserService {

	public boolean register(User user) throws Exception;
	
	public boolean login(User user) throws Exception;

	public String encryptToken(User user, HttpServletRequest request) throws Exception;

	public String decryptToken(String authToken) throws Exception;

	public User getUser(String userName) throws Exception;

	public boolean validateSession(User user, HttpServletRequest request,String authToken) throws Exception;

	public User getUser(int userId) throws Exception;
}
