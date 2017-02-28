package com.web.service;

import java.util.List;

import com.web.model.GameDetail;
import com.web.model.Request;


public interface GameService {

	public String addGame(Request request) throws Exception;

	public GameDetail getGameBySender(int senderId) throws Exception;

	public GameDetail getGame(String gameId) throws Exception;

	public boolean deleteGame(GameDetail game) throws Exception;

	public GameDetail drawFromDeck(GameDetail game) throws Exception;

	public boolean updateGame(GameDetail game) throws Exception;

	public GameDetail drawFromPile(GameDetail game) throws Exception;

	public GameDetail throwCard(GameDetail game, String cardId) throws Exception;

	public int checkWinner(GameDetail game) throws Exception;

	public List<String> getPlayerHand(GameDetail game, int userId) throws Exception;

}
