package org.apache.tapestry.Sampleproject.pages;

import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;

public class Admin {
	 @InjectComponent("Admin")	
	 private Form Adminform;
	 
	 @Property
	 private String uname;
	 
	 @Property
	 private String password;
	 
	 String username="Admin";
	 String pass="admin@1234";
	 
	 @OnEvent(value = "success", component = "Admin")
	 Object onSuccessFromSignup() {
		 if(username.equals(uname)&& pass.equals(password)) {
			 return Admindashboard.class;
		 }else {
			 return null;
		 }
	 }
}
