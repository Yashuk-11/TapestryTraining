package org.apache.tapestry.Sampleproject.pages;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tapestry5.FormValidationControl;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.ActivationRequestParameter;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.http.Link;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import com.example.model.User;
import com.example.service.Userservice;

public class Edit {
	
	private static final String pat = "^(http|https)://([a-zA-Z0-9.-]+(\\.[a-zA-Z]{2,4})+)(:[0-9]+)?(/[a-zA-Z0-9%_/.-]+)*$";
	
	@Property
	private FormValidationControl formValidationControl;
	
	@Inject
	private AlertManager alertmanager;
	
	@ActivationRequestParameter
    @Property
    private int id;
	
	@InjectComponent("cnumber")
    private TextField numberfield;
    @InjectComponent("url")
    private TextField urlfield;
	
	 @Property
	    private String name;

	    @Property
	    private String email;

	    @Property
	    private String password;

	    @Property
	    private Date bdate; // Assuming bdate is a Date

	    @Property
	    private String cnumber;
	    
		@Inject
	    private Userservice userService;
		
	    @Inject
	    private PageRenderLinkSource pageRenderLinkSource;
	    
	    @Property
	    private String url;
	
	    String jdbcUrl = "jdbc:mysql://localhost:3306/crimes";
	    String dbUsername = "root";
	    String dbPassword = "123456";
	    
	    private User user;
	    
	    @InjectComponent("Edit")
		private Form Edit;
	
	    //@OnActivate
	   void OnActivate(String email) {
		   System.out.println(email);
		  // this.email=email;
		   user=userService.getUserByEmail(email);
		   if(user==null) {  
			   System.out.println("hiiiiiiiiii");
		   }else {
			   System.out.println(email);
		   }
		//System.out.println(id);
		
 		
	}
    // You can use the "id" property in this page to retrieve user data
    // based on the ID passed from the source page.
	
	   @OnEvent(value = "success", component = "Edit")
		public Object Editdata() {
		    if (bdate != null&&url!=null&&cnumber!=null) {
		    	if(cnumber.length()!=10) {
		    		Edit.recordError(numberfield, "Contact number should be contain 10 digits");
		    	}
		    	if(isValidUrl(url)) {
		    	java.sql.Date sqlDate = new java.sql.Date(bdate.getTime());
		    	
		    	List<User> usersToInsert = new ArrayList<>(); 
                
                User newUser = new User();
                newUser.setName(name);
                newUser.setEmail(email);
                newUser.setPassword(password);
                newUser.setUrl(url);
                newUser.setBdate(sqlDate);
                newUser.setCnumber(cnumber);
             //   newUser.setFilepath(filePath);
                
                usersToInsert.add(newUser);
                userService.updateUserByEmail(usersToInsert);
					
				
		    	}
		    	Link targetPageLink = pageRenderLinkSource.createPageRenderLinkWithContext(Dashboard.class, name,email);

		          // Redirect to the target page
		          return targetPageLink;
		    }
		    return null;
	   }
	   
	   
	   public static boolean isValidUrl(String url) {
	        Pattern pattern = Pattern.compile(pat);
	        Matcher matcher = pattern.matcher(url);
	        return matcher.matches();
	    }
		
	
	
}
