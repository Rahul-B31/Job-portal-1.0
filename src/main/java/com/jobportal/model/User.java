package com.jobportal.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.jobportal.utility.AccountType;


@Document(collection = "users")
public class User {
	
	@Id
	private String user_id;
	private String name;
	
	@Indexed(unique = true )
	private String email;
	private String password;
	private AccountType accountType;
	
	
	public User(String name, String email, String password, AccountType accountType) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.accountType = accountType;
	}
	
	public User() {
	   
	}
	
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public AccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	

	
	

}
