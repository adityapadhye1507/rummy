package com.web.controller;

import java.sql.Date;
import java.sql.Timestamp;
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

import com.web.model.GameDetail;
import com.web.model.Request;
import com.web.model.Status;
import com.web.model.User;
import com.web.service.GameService;
import com.web.service.UserService;
import com.web.service.WaitingRoomService;

@Controller
@RequestMapping("/room")
public class WaitRoomController {

	@Autowired
	UserService userService;

	@Autowired
	WaitingRoomService roomService;

	@Autowired
	GameService gameService;

	private static final Logger logger = Logger.getLogger(WaitRoomController.class);

	/**
	 * Controller to get all the players in waiting room
	 * */
	@RequestMapping(value = "/getPlayers", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status getPlayers(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Inside getUsers waiting room!!");
		try {
			System.out.println("Fetching players!!");

			String authCode = request.getHeader("AuthToken");
			String userName = userService.decryptToken(authCode);

			List<String> players = roomService.getUsers(userName);
			if (players.size() == 0) {
				return new Status(0, "No users are playing the game!!!");
			} else {
				// Create a javascript array of players
				StringBuffer json = new StringBuffer("{\"players\": [");
				for (int i = 0; i < players.size(); i++) {
					if (i < players.size() - 1) {
						json.append("\"" + players.get(i) + "\"" + ",");
					} else {
						json.append("\"" + players.get(i) + "\"");
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
	 * Controller to request a player to play
	 * */
	@RequestMapping(value = "/requestPlayer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status requestPlayer(@RequestBody Request playerRequest, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("Inside requestPlayer waiting room!!");
		try {
			int senderId = userService.getUser(playerRequest.getSender()).getUserId();
			int recieverId = userService.getUser(playerRequest.getReciever()).getUserId();

			Request playerRequestToDb = new Request();
			playerRequestToDb.setSender(String.valueOf(senderId));
			playerRequestToDb.setReciever(String.valueOf(recieverId));

			if (roomService.addRequest(playerRequestToDb)) {
				return new Status(1, "Requesting the user! Wait for the user to accept your request!");
			} else {
				return new Status(0, "User Cannot be requested!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Status(0, "Exception in server!! Please check server logs!!");
		}
	}

	/**
	 * Controller to check if anyone has requested to play
	 * */
	@RequestMapping(value = "/getRequest", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status getRequest(@RequestBody Request playerRequest, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("Inside getRequest waiting room!!");
		try {
			int recieverId = userService.getUser(playerRequest.getReciever()).getUserId();

			Request playerRequestToDb = new Request();
			playerRequestToDb.setReciever(String.valueOf(recieverId));
			String sender = roomService.getRequest(playerRequestToDb);
			if (!sender.contentEquals("")) {
				User user = userService.getUser(Integer.parseInt(sender));
				return new Status(1, user.getUsername());
			} else {
				return new Status(0, "No user requested to play!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Status(0, "Exception in server!! Please check server logs!!");
		}
	}

	/**
	 * Controller to cancel the request
	 * */
	@RequestMapping(value = "/cancelRequest", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status cancelRequest(@RequestBody Request playerRequest, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("Inside cancelRequest waiting room!!");
		try {
			int senderId = userService.getUser(playerRequest.getSender()).getUserId();

			Request playerRequestToDb = new Request();
			playerRequestToDb.setSender(String.valueOf(senderId));
			if (roomService.cancelRequest(playerRequestToDb)) {
				return new Status(1, "Request Cancelled!!");
			} else {
				return new Status(0, "Request Couldn't be cancelled!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Status(0, "Exception in server!! Please check server logs!!");
		}
	}

	@RequestMapping(value = "/acceptRequest", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status acceptRequest(@RequestBody Request playerRequest, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("Inside acceptRequest waiting room!!");
		try {
			int recieverId = userService.getUser(playerRequest.getReciever()).getUserId();

			Request playerRequestToDb = new Request();
			playerRequestToDb.setReciever(String.valueOf(recieverId));
			playerRequestToDb = roomService.getRequestByReceiver(playerRequestToDb);
			if (roomService.acceptRequest(playerRequestToDb)) {
				String gameId = gameService.addGame(playerRequestToDb);
				return new Status(1, gameId);
			} else {
				return new Status(0, "Request Couldn't be Accepted!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Status(0, "Exception in server!! Please check server logs!!");
		}
	}

	/**
	 * Controller to check the status of the request sent by sender
	 * */
	@RequestMapping(value = "/checkStatus", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status checkStatus(@RequestBody Request playerRequest, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("Inside checkStatus waiting room!!");
		try {
			int senderId = userService.getUser(playerRequest.getSender()).getUserId();
			
			playerRequest.setSender(String.valueOf(senderId));
			Request requestDB = roomService.getRequestBySender(playerRequest);
			if(requestDB != null){
				// request is not accepted
				return new Status(0, "Request is pending!!");
			}
			
			GameDetail game = gameService.getGameBySender(senderId);
			if (game != null) {
				Timestamp lastActivity = game.getLastActivityTime();
				System.out.println(game.getLastActivityTime());
				System.out.println("last activity:"+ lastActivity.getTime());
				
				Date now = new Date(System.currentTimeMillis());
				System.out.println("now:"+ now.getTime());
				
				long inactiveTime = now.getTime() - lastActivity.getTime();
				System.out.println("difference:"+ inactiveTime);
				
				if (inactiveTime < 216000000) {
					return new Status(1, game.getGameId());
				} else {
					gameService.deleteGame(game);
					return new Status(0, "Last game inactive for more than 60 mins, waiting for new game!!");
				}
			} 
			
			if(requestDB == null && game == null){
				return new Status(2, "Request is cancelled by receiver!!");
			}
			
			else{
				return new Status(0, "Unknow State for the request!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Status(0, "Exception in server!! Please check server logs!!");
		}
	}
	
	/**
	 * Controller to logout a user from waiting room
	 * */
	@RequestMapping(value = "/logout", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Status logout(HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("Logging out from waiting room!!");
		try {
			String authToken = request.getHeader("AuthToken");
			String userName = userService.decryptToken(authToken);
			User user = userService.getUser(userName);

			if (roomService.logout(user.getUserId())) {
				return new Status(1, "User Logged out!!");
			} else {
				return new Status(0, "User Couldn't be logged out!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Status(0, "Exception in server!! Please check server logs!!");
		}
	}
}
