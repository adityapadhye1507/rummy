package com.data.dao;

import com.web.model.GameDetail;


public interface GameDao {
	public boolean addGame(GameDetail game) throws Exception;

	public GameDetail getGameById(String gameId) throws Exception;

	public GameDetail getGameBySender(int senderId) throws Exception;

	public boolean delete(GameDetail game) throws Exception;

	public boolean update(GameDetail game) throws Exception;
	
}
