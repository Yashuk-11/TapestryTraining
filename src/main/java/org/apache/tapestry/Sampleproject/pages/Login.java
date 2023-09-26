package org.apache.tapestry.Sampleproject.pages;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.hibernate.HibernateSessionSource;
import org.apache.tapestry5.http.Link;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.hibernate.*;
import com.example.service.Userservice;



public class Login {

    private static final Logger logger = LogManager.getLogger(Login.class);
    
    @Inject
    private PageRenderLinkSource linkSource;

    @Inject
    private PageRenderLinkSource pageRenderLinkSource;  
    
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

    @Inject
    private Session session;
    
    @Inject
    private Userservice userService;
    
    @Inject
    private HibernateSessionSource hibernateSessionSource;
    
    public Login(SessionFactory sessionFactory) {
    }
    public Object onValidateFromLogin() { 

    String name=userService.checkCredentials(email, password);
    if (name!=null) {
     
    	  alertManager.success("Login successfully");
     
    	  Link targetPageLink = pageRenderLinkSource.createPageRenderLinkWithContext(Dashboard.class, name,email);
          return targetPageLink;
      
    } else {
        
    	alertManager.error("Invalid email or password");
   	 	logger.error("User login failed!");
   	 	return null;
    }
}
}

