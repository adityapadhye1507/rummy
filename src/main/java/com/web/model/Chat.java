package com.web.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;

/**
 * The persistent class for the chats database table.
 * 
 */
@Entity
@Table(name = "chats")
@NamedQuery(name = "Chat.findAll", query = "SELECT c FROM Chat c")
public class Chat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "chat_id", unique = true, nullable = false)
	private String chatId;

	@Column(name = "game_id")
	private String gameId;

	@Column(length = 45)
	private String message;

	@Column(name = "reciever_id")
	private int recieverId;

	@Column(name = "sender_id")
	private int senderId;

	private Time timestamp;

	public Chat() {
	}

	public String getChatId() {
		return this.chatId;
	}

	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	public String getGameId() {
		return this.gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getRecieverId() {
		return this.recieverId;
	}

	public void setRecieverId(int recieverId) {
		this.recieverId = recieverId;
	}

	public int getSenderId() {
		return this.senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public Time getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Time timestamp) {
		this.timestamp = timestamp;
	}

}