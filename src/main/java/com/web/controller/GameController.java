package com.web.controller;

import java.util.List;

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

import com.web.model.Card;
import com.web.model.GameDetail;
import com.web.model.Status;
import com.web.model.User;
import com.web.service.GameService;
import com.web.service.UserService;
import com.web.service.WaitingRoomService;

@Controller
@RequestMapping("/game")
public class GameController {

	@Autowired
	UserService userService;

	@Autowired
	GameService gameService;
	
	@Autowired
	WaitingRoomService roomService;

	private static final Logger logger = Logger.getLogger(GameController.class);

	/**
	 * Controller to draw a card from the deck
	 * */
	@RequestMapping(value = "/drawDeck", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status drawFromDeck(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Inside Game Controller: Draw from deck!!");

		try {
			String gameId = request.getHeader("GameId");
			GameDetail game = gameService.getGame(gameId);
			String active = game.getActive();

			if (active.contentEquals("false")) {
				return new Status(0, "Game has been ended!!");
			}

			String authCode = request.getHeader("AuthToken");
			String userName = userService.decryptToken(authCode);

			int userId = userService.getUser(userName).getUserId();

			if (game.getActivePlayer() != userId) {
				return new Status(0, "Not your turn!! Please wait for the opponent to play their turn!!");
			}

			game = gameService.drawFromDeck(game);

			if (gameService.updateGame(game)) {
				return new Status(1, "Card Drawn from Deck succesfully!!");
			} else {
				return new Status(0, "Card Couldn't be drawn from deck!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Status(0, "Exception in server!! Please check server logs!!");
		}
	}

	/**
	 * Controller to draw a card from the pile
	 * */
	@RequestMapping(value = "/drawPile", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status drawFromPile(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Inside Game Controller: Draw from pile!!");

		try {
			String gameId = request.getHeader("GameId");
			GameDetail game = gameService.getGame(gameId);
			String active = game.getActive();

			if (active.contentEquals("false")) {
				return new Status(0, "Game has been ended!!");
			}

			String authCode = request.getHeader("AuthToken");
			String userName = userService.decryptToken(authCode);

			int userId = userService.getUser(userName).getUserId();

			if (game.getActivePlayer() != userId) {
				return new Status(0, "Not your turn!! Please wait for the opponent to play their turn!!");
			}

			game = gameService.drawFromPile(game);

			if (gameService.updateGame(game)) {
				return new Status(1, "Card Drawn from Pile succesfully!!");
			} else {
				return new Status(0, "Card Couldn't be drawn from Pile!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Status(0, "Exception in server!! Please check server logs!!");
		}
	}

	/**
	 * Controller to throw a card
	 * */
	@RequestMapping(value = "/throwCard", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status throwCard(@RequestBody Card card, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("Inside Game Controller: Throw Card!!");

		try {
			String gameId = request.getHeader("GameId");
			GameDetail game = gameService.getGame(gameId);
			String active = game.getActive();

			if (active.contentEquals("false")) {
				return new Status(0, "Game has been ended!!");
			}

			String authCode = request.getHeader("AuthToken");
			String userName = userService.decryptToken(authCode);

			int userId = userService.getUser(userName).getUserId();

			if (game.getActivePlayer() != userId) {
				return new Status(0, "Not your turn!! Please wait for the opponent to play their turn!!");
			}
			game = gameService.throwCard(game, card.getCardId());

			int winner = gameService.checkWinner(game);
			if (winner != -1) {
				game.setActive("false");

				// interchange the winner since we changed the active player
				// after throwing card
				if (winner == game.getPlayer1()) {
					winner = game.getPlayer2();
				} else {
					winner = game.getPlayer1();
				}

				game.setWinner(winner);

				User user = userService.getUser(winner);
			}

			if (gameService.updateGame(game)) {
				return new Status(1, "Card thrown succesfully!!");
			} else {
				return new Status(0, "Card Couldn't be thown!!");
			}
			// }
		} catch (Exception e) {
			e.printStackTrace();
			return new Status(0, "Exception in server!! Please check server logs!!");
		}
	}

	/**
	 * Controller to get the top card of the game
	 * */
	@RequestMapping(value = "/getTopCard", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status getTopCard(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Inside Game Controller: Get Top Card!!");

		try {
			String gameId = request.getHeader("GameId");
			GameDetail game = gameService.getGame(gameId);
			String topCard = game.getTopCard();
			return new Status(1, topCard);
		} catch (Exception e) {
			e.printStackTrace();
			return new Status(0, "Exception in server!! Please check server logs!!");
		}
	}

	@RequestMapping(value = "/getPlayerHand", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status getPlayerHand(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Inside Game Controller: Get Player's Hand!!");

		try {
			String authToken = request.getHeader("AuthToken");
			String userName = userService.decryptToken(authToken);
			User user = userService.getUser(userName);

			String gameId = request.getHeader("GameId");
			GameDetail game = gameService.getGame(gameId);

			List<String> hand = gameService.getPlayerHand(game, user.getUserId());

			StringBuffer json = new StringBuffer("{\"hand\": [");
			for (int i = 0; i < hand.size(); i++) {
				if (i < hand.size() - 1) {
					json.append("\"" + hand.get(i) + "\"" + ",");
				} else {
					json.append("\"" + hand.get(i) + "\"");
				}
			}
			json.append("]}");

			return new Status(1, json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return new Status(0, "Exception in server!! Please check server logs!!");
		}
	}

	/**
	 * Controller to get the user who won the game
	 * */
	@RequestMapping(value = "/getWinner", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status getWinner(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Inside Game Controller: Get Winner!!");

		try {
			String authToken = request.getHeader("AuthToken");
			String userName = userService.decryptToken(authToken);
			User user = userService.getUser(userName);

			String gameId = request.getHeader("GameId");
			GameDetail game = gameService.getGame(gameId);
			int winner = game.getWinner();
			if (winner == -1) {
				return new Status(0, "No winner yet!!");
			}
			String winnerName = userService.getUser(winner).getUsername();
			if (user.getUserId() == winner) {
				return new Status(1, "Congratulations, you won the game!!!");
			} else {
				return new Status(1, "Opponent " + winnerName + "won the game!!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new Status(0, "Exception in server!! Please check server logs!!");
		}
	}

	/**
	 * Controller to check if the player is allowed to play
	 * */
	@RequestMapping(value = "/checkTurn", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status checkTurn(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Inside Game Controller: Check Turn!!");

		try {
			String authToken = request.getHeader("AuthToken");
			String userName = userService.decryptToken(authToken);
			User user = userService.getUser(userName);

			String gameId = request.getHeader("GameId");
			GameDetail game = gameService.getGame(gameId);
			int activePlayer = game.getActivePlayer();
			if (user.getUserId() == activePlayer) {
				return new Status(1, "Its your turn to play!!!");
			}
			return new Status(0, "Not your turn to play!! Wait for the opponent to play!!");
		} catch (Exception e) {
			e.printStackTrace();
			return new Status(0, "Exception in server!! Please check server logs!!");
		}
	}
	
	/**
	 * Controller to forfit a match and send the user back to waiting room
	 * */
	@RequestMapping(value = "/logout", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status logout(HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("Logging out user!!!");

		try {
			String authToken = request.getHeader("AuthToken");
			String userName = userService.decryptToken(authToken);
			User user = userService.getUser(userName);

			String gameId = request.getHeader("GameId");
			if(gameId != null && !gameId.contentEquals("")){
				GameDetail game = gameService.getGame(gameId);
				game.setActive("false");
				if(user.getUserId() == game.getPlayer1()){
					game.setWinner(game.getPlayer2());
				}else{
					game.setWinner(game.getPlayer1());
				}
				
				if(gameService.deleteGame(game)){
					roomService.addPlayer(user.getUserId());
					return new Status(1, "User logged out successfully!!");
				}
			}			
			return new Status(0, "Invalid gameID!!");
		} catch (Exception e) {
			e.printStackTrace();
			return new Status(0, "Exception in server!! Please check server logs!!");
		}
	}
}
