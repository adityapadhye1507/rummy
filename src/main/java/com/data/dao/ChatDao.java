package com.data.dao;

import java.util.List;

import com.web.model.Chat;


public interface ChatDao {
	
	public List<Chat> getChats(String gameId) throws Exception;
	
	public boolean addChat(Chat chat) throws Exception;
}
