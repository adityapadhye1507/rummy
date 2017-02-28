package com.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
@RequestMapping("/registration")
public class RegistrationController {

	@Autowired
	UserService userService;

	private static final Logger logger = Logger.getLogger(RegistrationController.class);

	/**
	 * Controller to register a user
	 * */
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status addPlayer(@RequestBody User user, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("Inside register user!!");
		try {
			logger.info("Registering user!!" + user.getUsername());
			System.out.println(user.getUserId() + user.getUsername() + user.getPassword() + user.getDetails());
			if (null != userService.getUser(user.getUsername())) {
				return new Status(0, "Username already exisit in database!!!");
			}

			if (userService.register(user)) {
				String authToken = userService.encryptToken(user, request);
				//userService.login(user);
				return new Status(1, authToken);
			}

			return new Status(0, "Error: User did not get registered!!");
		} catch (Exception e) {
			e.printStackTrace();
			return new Status(0, "Exception in server!! Please check server logs!!");
		}

	}
}
