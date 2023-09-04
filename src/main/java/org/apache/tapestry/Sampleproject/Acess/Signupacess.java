package org.apache.tapestry.Sampleproject.Acess;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.tapestry.Sampleproject.model.Signupmodel;
public class Signupacess implements SignupAcces{
	 private Connection connection;

	    public Signupacess(Connection connection) {
	        this.connection = connection;
	    }

	    public void save(Signupmodel user) throws SQLException {
	        String query = "INSERT INTO users (name, email,password) VALUES (?,?, ?)";
	        
	        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            preparedStatement.setString(1, user.getName());
	            preparedStatement.setString(2, user.getEmail());
	            preparedStatement.setString(3, user.getPassword());
	            preparedStatement.executeUpdate();
	        }
	    }

		
}







