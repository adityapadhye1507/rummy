package com.web.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the request database table.
 * 
 */
@Entity
@Table(name="request")
@NamedQuery(name="Request.findAll", query="SELECT r FROM Request r")
public class Request implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private String sender;

	@Column
	private String reciever;

	public Request() {
	}

	public String getSender() {
		return this.sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReciever() {
		return this.reciever;
	}

	public void setReciever(String reciever) {
		this.reciever = reciever;
	}

}