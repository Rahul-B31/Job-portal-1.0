package com.jobportal.services;

import com.jobportal.model.User;


public interface IUserService {
	
//	public User findUserById(Integer id);
//	
//	public List<User> searchUsers(String query);
//	
	public User findUserProfile(String jwt);
//	
	public User registerUser(User user);
//	
//	public User updateUser(Integer userId,UpdateUserRequest red);

}
