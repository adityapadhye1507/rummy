package com.web.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.data.dao.RoomDao;
import com.data.dao.UserDao;
import com.web.model.User;
import com.web.model.WaitingRoom;
import com.web.service.UserService;
import com.web.util.EncoderUtil;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	@Autowired
	RoomDao roomDao;

	@Autowired
	EncoderUtil encoderUtil;

	/**
	 * Function to register a new user
	 */
	@Override
	public boolean register(User user) throws Exception {
		return userDao.addUser(user);
	}

	/**
	 * Function to login a user
	 */
	@Override
	public boolean login(User user) throws Exception {
		User dbUser = userDao.getUser(user.getUsername());
		if (dbUser != null) {
			System.out.println("User found: " + dbUser.getUsername());
			if (dbUser.getPassword().contentEquals(user.getPassword())) {
				// TODO: add player to the waiting room
				List<String> players = roomDao.getUsers();
				for (String id : players) {
					if (Integer.parseInt(id) == dbUser.getUserId()) {
						return true;
					}
				}
				WaitingRoom row = new WaitingRoom();
				row.setUserId(dbUser.getUserId());
				row.setInGame((byte) 0);
				return roomDao.add(row);
			}
		}
		System.out.println("User Not found in database!! Please register the user: " + user.getUsername());
		return false;
	}

	/**
	 * Function to encrypt a new token for the user
	 */
	@Override
	public String encryptToken(User user, HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		String userName = encoderUtil.encode(user.getUsername());
		String agent = encoderUtil.encode(request.getHeader("User-Agent"));
		User dbUser = this.getUser(user.getUsername());
		String details = encoderUtil.encode(dbUser.getDetails());

		StringBuffer buffer = new StringBuffer(userName);
		buffer.append(":::" + details);
		buffer.append(":::" + agent);

		System.out.println("Encrypted AuthToken:" + buffer.toString());
		return buffer.toString();
	}

	/**
	 * Function to decript token for the user
	 */
	@Override
	public String decryptToken(String authToken) throws Exception {
		// TODO Auto-generated method stub
		String[] encryptedTokens = authToken.split(":::");
		String encryptedUsername = encryptedTokens[0];
		String username = encoderUtil.decode(encryptedUsername);
		System.out.println("Decrypted Username: " + username);
		return username;
	}

	/**
	 * Function to get user by username
	 */
	@Override
	public User getUser(String userName) throws Exception {
		return userDao.getUser(userName);
	}

	/**
	 * Function to get user by user ID
	 */
	@Override
	public User getUser(int userId) throws Exception {
		return userDao.getUserById(userId);
	}

	/**
	 * Function to check if the user's session is valid
	 */
	@Override
	public boolean validateSession(User user, HttpServletRequest request, String authToken) throws Exception {
		if (authToken.contentEquals(encryptToken(user, request))) {
			return true;
		}
		return false;
	}

}
