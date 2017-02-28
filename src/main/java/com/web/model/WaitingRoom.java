package com.web.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the waiting_room database table.
 * 
 */
@Entity
@Table(name="waiting_room")
@NamedQuery(name="WaitingRoom.findAll", query="SELECT w FROM WaitingRoom w")
public class WaitingRoom implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="user_id", unique=true, nullable=false)
	private int userId;

	@Column(name="in_game")
	private byte inGame;

	public WaitingRoom() {
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public byte getInGame() {
		return this.inGame;
	}

	public void setInGame(byte inGame) {
		this.inGame = inGame;
	}

}