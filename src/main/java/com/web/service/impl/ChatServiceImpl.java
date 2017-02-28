package com.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.data.dao.ChatDao;
import com.data.dao.UserDao;
import com.web.model.Chat;
import com.web.service.ChatService;

@Service("chatService")
public class ChatServiceImpl implements ChatService {

	@Autowired
	UserDao userDao;
	
	@Autowired
	ChatDao chatDao;

	/**
	 * Function to get chat messages based on game ID
	 * */
	@Override
	public List<Chat> getMessages(String gameId) throws Exception {
		return chatDao.getChats(gameId);
	}

	/**
	 * Function to send a chat message to DB
	 * */
	@Override
	public boolean sendMessage(Chat chat) throws Exception {
		return chatDao.addChat(chat);
	}
	

}
