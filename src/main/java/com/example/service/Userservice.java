package com.example.service;

import java.util.List;
import com.example.model.User;

public interface Userservice {

	String checkCredentials(String email, String password);
	User getUserByEmail(String email);
	void insertUsers(List<User> users);
	void updateUserByEmail(List<User> users);
	void DeleteUserByEmail(String email);
	List<User> getUserDetails();
}
