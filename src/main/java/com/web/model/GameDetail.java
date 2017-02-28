package com.web.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the game_details database table.
 * 
 */
@Entity
@Table(name = "game_details")
@NamedQuery(name = "GameDetail.findAll", query = "SELECT g FROM GameDetail g")
public class GameDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "game_id", unique = true)
	private String gameId;
	
	@Column(name = "active")
	private String active;

	@Column(name = "active_player")
	private int activePlayer;

	@Column(name = "hand_p1", length = 255)
	private String handP1;

	@Column(name = "hand_p2", length = 255)
	private String handP2;

	@Column(name = "last_activity_time")
	private Timestamp lastActivityTime;

	@Column(name="pile", length = 255)
	private String pile;

	private int player1;

	private int player2;
	
	private int winner;

	@Column(name = "thrown_p1", length = 255)
	private String thrownP1;

	@Column(name = "thrown_p2", length = 255)
	private String thrownP2;

	@Column(name = "top_card")
	private String topCard;

	@Column(name = "deck")
	private String deck;

	public GameDetail() {
	}

	public String getGameId() {
		return this.gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public int getActivePlayer() {
		return this.activePlayer;
	}

	public void setActivePlayer(int activePlayer) {
		this.activePlayer = activePlayer;
	}

	public String getHandP1() {
		return this.handP1;
	}

	public void setHandP1(String handP1) {
		this.handP1 = handP1;
	}

	public String getHandP2() {
		return this.handP2;
	}

	public void setHandP2(String handP2) {
		this.handP2 = handP2;
	}

	public Timestamp getLastActivityTime() {
		return this.lastActivityTime;
	}

	public void setLastActivityTime(Timestamp lastActivityTime) {
		this.lastActivityTime = lastActivityTime;
	}

	public String getPile() {
		return this.pile;
	}

	public void setPile(String pile) {
		this.pile = pile;
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

	public String getThrownP1() {
		return this.thrownP1;
	}

	public void setThrownP1(String thrownP1) {
		this.thrownP1 = thrownP1;
	}

	public String getThrownP2() {
		return this.thrownP2;
	}

	public void setThrownP2(String thrownP2) {
		this.thrownP2 = thrownP2;
	}

	public String getTopCard() {
		return this.topCard;
	}

	public void setTopCard(String topCard) {
		this.topCard = topCard;
	}

	public void setDeck(String deck) {
		this.deck = deck;
	}

	public String getDeck() {
		return this.deck;
	}

	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}
}