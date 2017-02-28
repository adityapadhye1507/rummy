package com.web.service;

import java.util.List;

import com.web.model.Chat;


public interface ChatService {

	public List<Chat> getMessages(String gameId) throws Exception;
	
	public boolean sendMessage(Chat chat) throws Exception;
}
