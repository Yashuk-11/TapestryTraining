 package org.apache.tapestry.Sampleproject.pages;

import java.util.ArrayList;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.http.Link;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.hibernate.Session;
import com.example.model.User;
import com.example.service.Userservice;

public class Signup {

    private static final String pat = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
   
    @InjectComponent("Signup")	
    private Form Signupform;
   	
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
    
    @Inject
    private Userservice userService;
    
    @Property
    private String name;
    @Inject
    private Session session;
    
    @Inject
    private PageRenderLinkSource pageRenderLinkSource;

    @OnEvent(value = "success", component = "Signup")
    Object onSuccessFromSignup() {
    	
        if (isValidemail(email)) {
        	User u=userService.getUserByEmail(email);
        	if(u!=null) {
        		Signupform.recordError(emailField, "Entered email id already exists"); 
            	return null;
        	}
        	List<User> usersToInsert = new ArrayList<>();              
            User newUser = new User();
            newUser.setName(name);
            newUser.setEmail(email);
            newUser.setPassword(password);               
            usersToInsert.add(newUser);
            userService.insertUsers(usersToInsert);
            Link targetPageLink = pageRenderLinkSource.createPageRenderLinkWithContext(Userform.class, name, email);
            return targetPageLink;
        } else {
            Signupform.recordError(emailField, "Invalid email address.");
        }
        return null;
    }
    
    public static boolean isValidemail(String E) {
        Pattern pattern = Pattern.compile(pat);
        Matcher matcher = pattern.matcher(E);
        return matcher.matches();
    }
}
    
  

  





