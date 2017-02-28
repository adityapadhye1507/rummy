package com.data.dao;

import java.util.List;

import com.web.model.Request;
import com.web.model.WaitingRoom;

public interface RoomDao {
	public boolean add(WaitingRoom row) throws Exception;
	public boolean delete(WaitingRoom row) throws Exception;
	public boolean update(WaitingRoom row) throws Exception;
	public WaitingRoom getUser(int userId) throws Exception;
	public List<String> getUsers(int userId) throws Exception;
	public List<String> getUsers() throws Exception;
	public boolean addRequest(Request row) throws Exception;
	public boolean deleteRequest(Request row) throws Exception;
	public Request getRequestBySender(String senderId) throws Exception;
	public Request getRequestByReceiver(String recieverId) throws Exception;
}
