package com.web.service;

import java.util.List;

import com.web.model.Request;

public interface WaitingRoomService {

	public List<String> getUsers(String user) throws Exception;

	public boolean addRequest(Request playerRequestToDb) throws Exception;

	public String getRequest(Request playerRequestToDb) throws Exception;

	public boolean cancelRequest(Request playerRequestToDb) throws Exception;

	public boolean acceptRequest(Request playerRequestToDb) throws Exception;

	public Request getRequestByReceiver(Request playerRequestToDb) throws Exception;

	public Request getRequestBySender(Request playerRequestToDb) throws Exception;

	public boolean logout(int userId) throws Exception;

	public boolean addPlayer(int userId) throws Exception;
	
}
