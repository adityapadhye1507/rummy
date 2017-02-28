package com.web.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the cards database table.
 * 
 */
@Entity
@Table(name="cards")
@NamedQuery(name="Card.findAll", query="SELECT c FROM Card c")
public class Card implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="card_id", unique=true, nullable=false)
	private String cardId;

	@Column(name="card_name", length=45)
	private String cardName;

	@Column(length=45)
	private String image;
	
	private Character suit;
	
	private Integer number;

	public Card() {
	}

	public String getCardId() {
		return this.cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getCardName() {
		return this.cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Character getSuit() {
		return suit;
	}

	public void setSuit(Character suit) {
		this.suit = suit;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

}