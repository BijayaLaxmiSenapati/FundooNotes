package com.bridgelabz.fundoonotes.user.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

/**
*
* @author Bijaya Laxmi Senapati
* @since 10/07/2018
* @version 1.0
*
*/
@Document(collection = "User")
public class User {
	@Id
	private String id;
	
	private String name;

	private String email;
	
	private String contactNumber;
	
	private String password;
	
	private boolean activationStatus;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isActivationStatus() {
		return activationStatus;
	}

	public void setActivationStatus(boolean activationStatus) {
		this.activationStatus = activationStatus;
	}
	

}
