package com.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.data.dao.RoomDao;
import com.data.dao.UserDao;
import com.web.model.Request;
import com.web.model.WaitingRoom;
import com.web.service.WaitingRoomService;

@Service("roomService")
public class WaitingRoomServiceImpl implements WaitingRoomService {

	@Autowired
	UserDao userDao;

	@Autowired
	RoomDao roomDao;

	
	/**
	 * Function to get all the users in waiting room
	 * */
	@Override
	public List<String> getUsers(String user) throws Exception {
		int userId = userDao.getUser(user).getUserId();
		List<String> userIdList = roomDao.getUsers(userId);
		System.out.println("UserID List:" + userIdList);
		List<String> userNameList = new ArrayList<String>();
		for (String id : userIdList) {
			String username = userDao.getUserById(Integer.parseInt(id)).getUsername();
			userNameList.add(username);
		}
		return userNameList;
	}

	/**
	 * Function to add a request to the DB
	 * */
	@Override
	public boolean addRequest(Request playerRequestToDb) throws Exception {

		Request row = roomDao.getRequestBySender(playerRequestToDb.getSender());

		if (row == null) {
			roomDao.addRequest(playerRequestToDb);
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Function to check if the requester has any request entry in DB
	 * */
	@Override
	public String getRequest(Request playerRequestToDb) throws Exception {
		Request row = roomDao.getRequestByReceiver(playerRequestToDb.getReciever());
		if (row != null) {
			return row.getSender();
		} else {
			return "";
		}
	}

	/**
	 * Function to get request for the receiver
	 * */
	@Override
	public Request getRequestByReceiver(Request playerRequestToDb) throws Exception {
		return roomDao.getRequestByReceiver(playerRequestToDb.getReciever());
	}

	/**
	 * Function to get request for the sender
	 * */
	@Override
	public Request getRequestBySender(Request playerRequestToDb) throws Exception {
		return roomDao.getRequestBySender(playerRequestToDb.getSender());
	}
	
	/**
	 * Function to cancel the request
	 * */
	@Override
	public boolean cancelRequest(Request playerRequestToDb) throws Exception {
		Request row = roomDao.getRequestBySender(playerRequestToDb.getSender());
		return roomDao.deleteRequest(row);
	}

	/**
	 * Function to accept the request
	 * */
	@Override
	public boolean acceptRequest(Request playerRequestToDb) throws Exception {
		Request row = roomDao.getRequestByReceiver(playerRequestToDb.getReciever());
		WaitingRoom sender = roomDao.getUser(Integer.parseInt(row.getSender()));
		sender.setInGame(new Byte("1"));
		roomDao.update(sender);
		WaitingRoom receiver = roomDao.getUser(Integer.parseInt(row.getReciever()));
		receiver.setInGame(new Byte("1"));
		roomDao.update(receiver);
		return roomDao.deleteRequest(row);
	}

	/**
	 * Function to logout the user and send them to login
	 * */
	@Override
	public boolean logout(int userId) throws Exception {
		WaitingRoom row = new WaitingRoom();
		row.setUserId(userId);
		return roomDao.delete(row);
	}

	/**
	 * Function to add a player to waiting room
	 * */
	@Override
	public boolean addPlayer(int userId) throws Exception {
		WaitingRoom row = new WaitingRoom();
		row.setUserId(userId);
		row.setInGame(new Byte("0"));
		return roomDao.update(row);
	}
}
