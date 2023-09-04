package org.apache.tapestry.Sampleproject.pages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tapestry.Sampleproject.Acess.SignupAcces;
import org.apache.tapestry.Sampleproject.Acess.Signupacess;
import org.apache.tapestry.Sampleproject.model.Signupmodel;
//import org.apache.tapestry.Sampleproject.services.Item;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;




public class Signup {

    private static final Logger logger = LogManager.getLogger(Signup.class);
    
  // @Inject
  //  private SignupAcces signupacess;
    
  //  @Inject
//    Session session;
    
    @Inject
    private AlertManager alertManager;

   @InjectComponent("Signup")
   // @Component
    private Form Signupform;
   	
   //	@Property
 //  	private Signupmodel newitem;

    @InjectComponent("email")
    private TextField emailField;

    @InjectComponent("password")
    private PasswordField passwordField;
    
    @InjectComponent("name")
    private TextField namefield;

    @Property
    private String email;

    @Property
    private String password;
    
    @Property
    private String name;

    
    @OnEvent(value = "success", component = "Signup")
    Object onSuccessFromSignup() {
    	 if (name != null && !name.isEmpty() && email != null && !email.isEmpty() && password != null && !password.isEmpty()) {
    		// alertManager.success("User registration successful!");
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String jdbcUrl = "jdbc:mysql://localhost:3306/crimes";
	         String dbUsername = "root";
	         String dbPassword = "123456";

	         try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
	        	
	             String insertQuery = "INSERT INTO user (name, email, password) VALUES (?, ?, ?)";

	         PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
	             preparedStatement.setString(1, name);
	             preparedStatement.setString(2, email);
	             preparedStatement.setString(3, password);
	             int i=preparedStatement.executeUpdate();
	           //  connection.commit();
	             if(i>0) {
	            	 alertManager.success("User registration successful!");
	                 return Login.class;
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
    	 }
         return About.class;    
}
    	/*
    	//return Index.class;
    	 try {
             // Save the new user to the database
             signupacess.save(newitem);

             // Clear the form
             //Signupform.clear();

             // Optionally, you can return a response or navigate to another page
             logger.info("Registration successful!");
             alertManager.success("Welcome aboard!");

             return Index.class;
         } catch (SQLException e) {
             // Handle database error
             logger.error("Error saving user to the database.", e);
             alertManager.error("Sorry, an error occurred during registration.");
             return null; // Stay on the same page
       // logger.info("Login successful!");
        //alertManager.success("Welcome aboard!");
        
        //return Index.class;
         }
        */
    }
    
  

  





