package org.apache.tapestry.Sampleproject.pages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;

public class Login {

    private static final Logger logger = LogManager.getLogger(Login.class);

    @Inject
    private AlertManager alertManager;

    @InjectComponent
    private Form login;

    @InjectComponent("email")
    private TextField emailField;

    @InjectComponent("password")
    private PasswordField passwordField;

    @Property
    private String email;

    @Property
    private String password;

    Object onValidateFromLogin() {
    	
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String jdbcUrl = "jdbc:mysql://localhost:3306/crimes";
	         String dbUsername = "root";
	         String dbPassword = "123456";

	         try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
	        	 connection.setAutoCommit(false);
	             // Create a SQL query to insert data into the database
	             String insertQuery = "SELECT email,password from user where email=? and password=?";

	         PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
	            preparedStatement.setString(1, email);
	            preparedStatement.setString(2, password);
	             ResultSet i=preparedStatement.executeQuery();
	             connection.commit();
	             if(i.next()) {
	            	 alertManager.success("User login successful!");
	                 return Dashboard.class;
	             }
	             else {
	            	// login.recordError(emailField, "Try with user: users@tapestry.apache.org");
	            	 logger.error("User login failed!");
	            	 return Index.class;
	             }
	             
	 	    } catch (SQLException e) {
	 	        // Handle database error
	 	        logger.error("Error saving user to the database.", e);
	 	        alertManager.error("Sorry, an error occurred during registration.");
	 	        // Stay on the same page or redirect to an error page
	 	    }
	 		} catch (ClassNotFoundException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		}
    	return ((Object) login).getClass();
}
    	/*
        if (!email.equals("yk0134065@gmail.com"))
            login.recordError(emailField, "Try with user: users@tapestry.apache.org");

        if (!password.equals("Tapestry5"))
            login.recordError(passwordField, "Try with password: Tapestry5");*/
    

    Object onSuccessFromLogin() {
        logger.info("Login successful!");
        alertManager.success("Welcome aboard!");
        return Index.class;
    }

    void onFailureFromLogin() {
        logger.warn("Login error!");
        alertManager.error("I'm sorry but I can't log you in!");
    }
}
