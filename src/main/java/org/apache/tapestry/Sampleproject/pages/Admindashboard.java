package org.apache.tapestry.Sampleproject.pages;

import java.util.List;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import com.example.model.User;
import com.example.service.Userservice;

public class Admindashboard {

    @Inject
    private Userservice userService;
    
    @Property
    private User user; 

    @Property
    private List<User> users;

    void setupRender() {
        users = userService.getUserDetails();
    }

    Object onActionFromDeleteLink(String email) {
    	userService.DeleteUserByEmail(email);
    	return this; 
    }

}

