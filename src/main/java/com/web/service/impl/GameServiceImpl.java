package com.web.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.data.dao.CardDao;
import com.data.dao.GameDao;
import com.web.model.Card;
import com.web.model.GameDetail;
import com.web.model.Request;
import com.web.service.GameService;
import com.web.util.ConstantsDao;

@Service("gameService")
public class GameServiceImpl implements GameService {

	@Autowired
	GameDao gameDao;

	@Autowired
	CardDao cardDao;

	/**
	 * Function to add a new Game in DB
	 * */
	@Override
	public String addGame(Request request) throws Exception {
		GameDetail game = new GameDetail();

		String id = UUID.randomUUID().toString().replace("-", "");
		game.setGameId(id);
		game = initGame(game);
		game.setPlayer1(Integer.parseInt(request.getSender()));
		game.setPlayer2(Integer.parseInt(request.getReciever()));
		game.setActivePlayer(Integer.parseInt(request.getSender()));
		game.setActive("true");
		Timestamp now = new Timestamp(System.currentTimeMillis());
		game.setLastActivityTime(now);
		gameDao.addGame(game);

		return id;
	}

	/**
	 * Function to init GameDetail object
	 * */
	private GameDetail initGame(GameDetail game) throws Exception {

		List<String> cards = cardDao.getCards();
		Collections.shuffle(cards);

		List<String> p1Hand = new ArrayList<String>();
		List<String> p2Hand = new ArrayList<String>();
		for (int i = 0; i < 14; i = i + 2) {
			p1Hand.add(cards.remove(i));
			p2Hand.add(cards.remove(i + 1));
		}
		String topCard = cards.remove(0);
		String p1HandString = cardDao.toCsv(p1Hand);
		String p2HandString = cardDao.toCsv(p2Hand);
		String deckString = cardDao.toCsv(cards);
		String pile = topCard + ",";

		game.setDeck(deckString);
		game.setHandP1(p1HandString);
		game.setHandP2(p2HandString);
		game.setTopCard(topCard);
		game.setPile(pile);
		game.setWinner(-1);

		return game;
	}

	/**
	 * Function to get game object by user ID
	 * */
	@Override
	public GameDetail getGameBySender(int senderId) throws Exception {
		return gameDao.getGameBySender(senderId);
	}

	/**
	 * Function to get game object from DB by game ID
	 * */
	@Override
	public GameDetail getGame(String gameId) throws Exception {
		return gameDao.getGameById(gameId);
	}

	/**
	 * Function to soft delete the game in DB
	 * */
	@Override
	public boolean deleteGame(GameDetail game) throws Exception {
		return gameDao.delete(game);
	}

	/**
	 * Function to draw a card from the deck
	 * Changes the game object, the deck and player's hand
	 * */
	@Override
	public GameDetail drawFromDeck(GameDetail game) throws Exception {
		int activePlayer = game.getActivePlayer();

		String deckString = game.getDeck();
		List<String> deck = cardDao.toList(deckString);
		String card = deck.remove(0);

		if (deck.size() == 0) {
			String pileString = game.getPile();
			List<String> pile = cardDao.toList(pileString);
			pile.remove(pile.size() - 1);
			deck = pile;
			Collections.shuffle(deck);
			game.setPile(game.getTopCard() + ",");
		}

		game.setDeck(cardDao.toCsv(deck));

		if (activePlayer == game.getPlayer1()) {
			String hand = game.getHandP1();
			if (hand.split(",").length < 8) {
				hand += card + ",";
				game.setHandP1(hand);
			}
		} else {
			String hand = game.getHandP2();
			if (hand.split(",").length < 8) {
				hand += card + ",";
				game.setHandP2(hand);
			}
		}
		Timestamp now = new Timestamp(System.currentTimeMillis());
		game.setLastActivityTime(now);
		return game;
	}

	/**
	 * Function to get top card on the pile
	 * Changes the Player's card in hand
	 * */
	@Override
	public GameDetail drawFromPile(GameDetail game) throws Exception {
		int activePlayer = game.getActivePlayer();

		String pileString = game.getPile();
		List<String> pile = cardDao.toList(pileString);
		String card = pile.remove(pile.size() - 1);
		game.setPile(cardDao.toCsv(pile));

		String topCard = "noCard";

		if (!pile.isEmpty()) {
			topCard = pile.get(pile.size() - 1);
		}
		game.setTopCard(topCard);

		if (activePlayer == game.getPlayer1()) {
			String hand = game.getHandP1();
			if (hand.split(",").length < 8) {
				hand += card + ",";
				game.setHandP1(hand);
			}
		} else {
			String hand = game.getHandP2();
			if (hand.split(",").length < 8) {
				hand += card + ",";
				game.setHandP2(hand);
			}
		}
		Timestamp now = new Timestamp(System.currentTimeMillis());
		game.setLastActivityTime(now);
		return game;
	}

	/**
	 * Function to update the game object in DB
	 * */
	@Override
	public boolean updateGame(GameDetail game) throws Exception {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		game.setLastActivityTime(now);
		return gameDao.update(game);
	}

	/**
	 * Function to Throw a card from the player's hand
	 * Check for the winner and set a winner for the game after the turn is over
	 * */
	@Override
	public GameDetail throwCard(GameDetail game, String cardId) throws Exception {
		int activePlayer = game.getActivePlayer();

		if (activePlayer == game.getPlayer1()) {
			List<String> hand = cardDao.toList(game.getHandP1());
			if (hand.size() == 8) {
				int index = hand.indexOf(cardId);
				hand.remove(index);
				game.setHandP1(cardDao.toCsv(hand));
				String pileString = game.getPile();
				List<String> pile = cardDao.toList(pileString);
				pile.add(cardId);
				game.setPile(cardDao.toCsv(pile));
				game.setTopCard(cardId);
				game.setActivePlayer(game.getPlayer2());
			}
		} else {
			List<String> hand = cardDao.toList(game.getHandP2());
			if (hand.size() == 8) {
				int index = hand.indexOf(cardId);
				hand.remove(index);
				game.setHandP2(cardDao.toCsv(hand));
				String pileString = game.getPile();
				List<String> pile = cardDao.toList(pileString);
				pile.add(cardId);
				game.setPile(cardDao.toCsv(pile));
				game.setTopCard(cardId);
				game.setActivePlayer(game.getPlayer1());
			}
		}

		Timestamp now = new Timestamp(System.currentTimeMillis());
		game.setLastActivityTime(now);

		int winner = checkWinner(game);
		// if winner != -1, update winner in game
		if (winner != -1) {
			game.setWinner(winner);
			gameDao.update(game);
		}
		return game;
	}

	/**
	 * Function to check for a winner after the card is thrown
	 * */
	@Override
	public int checkWinner(GameDetail game) throws Exception {
		int player = game.getActivePlayer();

		// toggle the player since we already changed the active player after
		// throwing the card
		String handString;
		// get the hand of the active player
		if (player == game.getPlayer1()) {
			handString = game.getHandP2();
		} else {
			handString = game.getHandP1();
		}
		List<String> hand = cardDao.toList(handString);

		// Get all cards in hand
		List<Card> cards = new ArrayList<Card>();
		for (String string : hand) {
			Character suit = (Character) string.toCharArray()[0];
			Integer num = Integer.parseInt(string.substring(1, 3));
			Card card = new Card();
			card.setCardId(string);
			card.setSuit(suit);
			card.setNumber(num);
			cards.add(card);
		}

		// Check if the cards have a 3 of a kind and a 4 card sequence
		if (threeOfAKind(cards)) {
			return game.getActivePlayer();
		}

		// For a particular suit of cards, check for the winning sequences

		Character[] suits = { 'S', 'H', 'C', 'D' };
		for (Character suit : suits) {
			if (checkSuitForSequence(suit, cards)) {
				return game.getActivePlayer();
			}
		}

		return -1;
	}

	/**
	 * Function to check if the player's hand has a 3 of a kind and a 4 sequence
	 * */
	private boolean threeOfAKind(List<Card> cards) {
		int counter = 0;
		List<Card> list = new ArrayList<Card>();
		
		for (int i = 0; i < cards.size(); i++) {
			for (int j = i + 1; j < cards.size(); j++) {
				if (cards.get(i).getNumber() == cards.get(j).getNumber()) {
					if(!list.contains(cards.get(j))){
						list.add(cards.get(j));
					}
					if (counter == 0) {
						list.add(cards.get(i));
					}
					counter++;
				}
			}
		}
		if (list.size() == 4) {
			// Four of a kind!!!
			// check 4 sequence for every 3 of a kind pair
			for (Card card : list) {
				List<Card> dummyList = new ArrayList<Card>();
				List<Card> dummyCardList = new ArrayList<Card>();
				dummyCardList.addAll(cards);
				dummyList.addAll(list);
				dummyList.remove(card);
				dummyCardList.removeAll(dummyList);
				if (fourSequence(dummyCardList)) {
					return true;
				}
			}
			return false;
		}
		if (list.size() == 3) {
			// three of a kind!!
			// check for a 4 sequence

			// remove the 3 of a kind cards
			cards.removeAll(list);
			return fourSequence(cards);
		}
		return false;
	}

	/**
	 * Function to check if the hand has a 4 sequence
	 * */
	private boolean fourSequence(List<Card> cards) {

		// check if all cards belong to same suit
		Character[] suits = { 'S', 'H', 'C', 'D' };
		boolean sameSuit = false;
		for (Character suit : suits) {
			int i = 0;
			for (Card card : cards) {
				if (card.getSuit().equals(suit)) {
					i++;
				}
			}
			if (i == 4) {
				sameSuit = true;
			}
		}
		if (sameSuit) {
			// All cards belong to same suit
			// check for a 4 sequence
			List<Integer> intList = new ArrayList<Integer>();
			for (Card card : cards) {
				intList.add(card.getNumber());
			}
			List<List<Integer>> result = findFourSequence(intList);
			if (result.size() != 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Function to check if the hand has a full sequence or a 4 sequence for any particular suit
	 * */
	private boolean checkSuitForSequence(Character suitName, List<Card> cards) {

		// suit is the list of cards with same suit
		List<Integer> suit = getList(suitName, cards);
		Collections.sort(suit);

		
		// check if all the cards are in a sequence
		List<Integer> sequence = findFullSequence(suit); // if all cards are in
															// sequence
		if (sequence.size() == 7) {
			return true;
		}

		// check for 1 Possible case of sequence of 4
		if (suit.size() == 4) {
			if (findFourSequence(suit).size() != 0) {
				// sequence of 4 cards in same suit
				// remove these 4 cards from all cards, and check for a sequence
				cards = removeCard(cards, suit, suitName);
				// of 3 cards in remaining 3 cards
				return threeSequence(cards);
			}
			return false;
		}
		return false;
	}
	
	/**
	 * Function to check if the hand has a 3 sequence
	 * */
	private boolean threeSequence(List<Card> cards) {
		// check if all cards belong to same suit
		Character[] suits = { 'S', 'H', 'C', 'D' };
		boolean sameSuit = false;
		for (Character suit : suits) {
			int i = 0;
			for (Card card : cards) {
				if (card.getSuit().equals(suit)) {
					i++;
				}
			}
			if (i == 3) {
				sameSuit = true;
			}
		}
		if (sameSuit) {
			// All cards belong to same suit
			// check for a 4 sequence
			List<Integer> intList = new ArrayList<Integer>();
			for (Card card : cards) {
				intList.add(card.getNumber());
			}
			List<List<Integer>> result = findThreeSequence(intList);
			if (result.size() != 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Function to find a sequence of 3 numbers for a particular suit
	 * */
	private List<List<Integer>> findThreeSequence(List<Integer> suit) {
		Collections.sort(suit);
		ArrayList<List<Integer>> sequences = new ArrayList<List<Integer>>();
		for (List<Integer> list : ConstantsDao.threeSequence) {
			if (suit.equals(list)) {
				sequences.add(list);
			}
		}
		return sequences;
	}

	/**
	 * Function to remove the cards already detected as a sequence
	 * */
	private List<Card> removeCard(List<Card> cards, List<Integer> suit, Character suitName) {
		List<Card> dummy = new ArrayList<Card>();
		dummy.addAll(cards);
		for (Card card : cards) {
			for (Integer integer : suit) {
				if (card.getSuit().equals(suitName) && card.getNumber() == integer) {
					dummy.remove(card);
				}
			}
		}
		return dummy;
	}

	/**
	 * Function to find a sequence of 7 numbers for a particular suit
	 * */
	private List<Integer> findFullSequence(List<Integer> suit) {
		List<Integer> sequence = new ArrayList<Integer>();
		Collections.sort(suit);
		if (suit.size() == 7) {
			for (List<Integer> list : ConstantsDao.fullSequence) {
				if (suit.equals(list)) {
					return suit;
				}
			}
		}
		return sequence;
	}

	/**
	 * Function to find a sequence of 4 numbers for a particular suit
	 * */
	// Assuming calling function is sending a list of 4 cards belonging to same
	// suit
	private ArrayList<List<Integer>> findFourSequence(List<Integer> suit) {
		Collections.sort(suit);
		ArrayList<List<Integer>> sequences = new ArrayList<List<Integer>>();
		for (List<Integer> list : ConstantsDao.fourSequence) {
			if (suit.equals(list)) {
				sequences.add(list);
			}
		}
		return sequences;
	}

	/**
	 * Function to convert list of cards of a particular suit to list of Integer
	 * */
	private List<Integer> getList(Character suit, List<Card> cards) {
		List<Integer> list = new ArrayList<Integer>();
		for (Card card : cards) {
			if (card.getSuit().equals(suit)) {
				list.add(card.getNumber());
			}
		}
		return list;
	}

	/**
	 * Function to get card ID's in the player's hand
	 * */
	@Override
	public List<String> getPlayerHand(GameDetail game, int userId) throws Exception {
		List<String> hand = new ArrayList<String>();
		String handString = "";
		if (game.getPlayer1() == userId) {
			handString = game.getHandP1();
		} else {
			handString = game.getHandP2();
		}

		String[] handArr = handString.split(",");
		for (int i = 0; i < handArr.length; i++) {
			hand.add(handArr[i]);
		}
		return hand;
	}

}
