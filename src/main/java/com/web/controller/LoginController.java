package com.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.model.Status;
import com.web.model.User;
import com.web.service.UserService;

@Controller
@RequestMapping("/")
public class LoginController {

	@Autowired
	UserService userService;

	/**
	 * Controller to login a user
	 * */
	@RequestMapping(value = "login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status login(@RequestBody User user, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (userService.login(user)) {
				String authToken = userService.encryptToken(user, request);
				return new Status(1, authToken);
			} else {
				return new Status(0, "Cannot Login user!! Invalid Credentials!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Status(0, "Exception in server!! Please check server logs!!");
		}
	}
}
