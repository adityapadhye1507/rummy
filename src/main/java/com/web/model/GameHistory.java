package com.web.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;


/**
 * The persistent class for the game_history database table.
 * 
 */
@Entity
@Table(name="game_history")
@NamedQuery(name="GameHistory.findAll", query="SELECT g FROM GameHistory g")
public class GameHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	private Time endTime;

	@Id
	@Column(name="game_id", nullable=false)
	private String gameId;

	@Column(nullable=false)
	private int player1;

	@Column(nullable=false)
	private int player2;

	private int winner;

	public GameHistory() {
	}

	public Time getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	public String getGameId() {
		return this.gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public int getPlayer1() {
		return this.player1;
	}

	public void setPlayer1(int player1) {
		this.player1 = player1;
	}

	public int getPlayer2() {
		return this.player2;
	}

	public void setPlayer2(int player2) {
		this.player2 = player2;
	}

	public int getWinner() {
		return this.winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

}