package com.web.controller;

import java.sql.Time;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.model.Chat;
import com.web.model.GameDetail;
import com.web.model.Status;
import com.web.service.ChatService;
import com.web.service.GameService;
import com.web.service.UserService;

@Controller
@RequestMapping("/chat")
public class ChatController {

	@Autowired
	UserService userService;

	@Autowired
	GameService gameService;

	@Autowired
	ChatService chatService;

	private static final Logger logger = Logger.getLogger(ChatController.class);

	/**
	 * Controller to get all chat messages for a game
	 * */
	@RequestMapping(value = "/getMessages", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status getMessages(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Inside getMessages chat Controller!!");
		try {

			String gameId = request.getHeader("GameId");
			if(gameId.contentEquals("null")){
				return new Status(0, "No Game ID header available!!");
			}
			List<Chat> messages = chatService.getMessages(gameId);

			if (messages.size() == 0) {
				return new Status(0, "No Chats available!!");
			} else {
				// Create a javascript array of players
				StringBuffer json = new StringBuffer("{\"players\": [");
				for (int i = 0; i < messages.size(); i++) {
					String sender = userService.getUser(messages.get(i).getSenderId()).getUsername();
					String message = sender + " says:" + messages.get(i).getMessage();
					if (i < messages.size() - 1) {
						json.append("\"" + message + "\"" + ",");
					} else {
						json.append("\"" + message + "\"");
					}
				}
				json.append("]}");
				return new Status(1, json.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Status(0, "Exception in server!! Please check server logs!!");
		}
	}

	/**
	 * Controller to send a chat message
	 * */
	@RequestMapping(value = "/sendMessage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status sendMessage(@RequestBody Status data, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("Inside sendMessage chat Controller!!");

		try {
			if (data.getMessage() == null || data.getMessage().contentEquals("")) {
				return new Status(0, "Chat message is empty!!");
			}

			String authCode = request.getHeader("AuthToken");
			String senderName = userService.decryptToken(authCode);
			int sender = userService.getUser(senderName).getUserId();

			int reciever = -1;

			String gameId = request.getHeader("gameId");

			GameDetail game = gameService.getGame(gameId);
			if (game != null) {
				if (game.getPlayer1() == sender) {
					reciever = game.getPlayer2();
				} else {
					reciever = game.getPlayer1();
				}
				String id = UUID.randomUUID().toString().replace("-", "");

				Chat chat = new Chat();
				chat.setGameId(gameId);
				chat.setSenderId(sender);
				chat.setRecieverId(reciever);
				chat.setTimestamp(new Time(System.currentTimeMillis()));

				String cleanMessage = StringEscapeUtils.escapeHtml4(data.getMessage().trim());
				cleanMessage = StringUtils.substring(cleanMessage, 0, 255);
				chat.setMessage(cleanMessage);
				chat.setChatId(id);

				if (chatService.sendMessage(chat)) {
					return new Status(1, "Chat message sent!!");
				} else {
					return new Status(0, "Chat message couldn't be sent!!");
				}
			} else {
				return new Status(0, "Illegal game ID!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Status(0, "Exception in server!! Please check server logs!!");
		}
	}
}
