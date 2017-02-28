package com.data.dao;

import com.web.model.User;


public interface UserDao {
	public boolean addUser(User user) throws Exception;
	
	public User getUser(String userName) throws Exception;

	boolean deleteUser(int id) throws Exception;

	User getUserById(int userId) throws Exception;
}
