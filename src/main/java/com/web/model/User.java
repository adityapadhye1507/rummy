package com.web.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Users database table.
 * 
 */
@Entity
@Table(name="Users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="user_id", unique=true, nullable=false)
	private int userId;

	@Column(length=45)
	private String details;

	@Column(length=45)
	private String password;

	@Column(length=45)
	private String username;

	public User() {
	}

	public int getUserId() {
		return this.userId;
	}

	public String getDetails() {
		return this.details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}