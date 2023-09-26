package org.apache.tapestry.Sampleproject.pages;

import java.io.File;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tapestry5.FormValidationControl;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.upload.components.Upload;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.hibernate.Session;

import com.example.model.User;
import com.example.service.Userservice;


public class Userform {
	
	private static final String pat = "^(http|https)://([a-zA-Z0-9.-]+(\\.[a-zA-Z]{2,4})+)(:[0-9]+)?(/[a-zA-Z0-9%_/.-]+)*$";
	
	@Inject
	private AlertManager alertManager;
	
    @Inject
    private PageRenderLinkSource pageRenderLinkSource;
	@Property
	private FormValidationControl formValidationControl;
	
	@InjectComponent("Userform")
	private Form Userform;
	
	@InjectComponent("name")
    private TextField namefield;
	
	@InjectComponent("number")
    private TextField numberfield;
    
    @InjectComponent("url")
    private TextField urlfield;
    
    @InjectComponent("file")
    private Upload filefield;
    
    @Property
	private String number;
	
	@Property
	private String url;
	
	@Property
	private Date bdate;
	
	@Property
	private UploadedFile file;
	
	@Property
    private String name;
	
	@Inject
	private Userservice userService;
	
	@Property
    private String email;
	
	@Inject
	private Session session;
	
    void onActivate(String name,String email) {
        this.name = name;
        this.email=email;
    }
	
	@OnEvent(value = "success", component = "Userform")
	public Object uploadFile() {
	    if (bdate != null&&file != null&&url!=null&&number!=null) {
	    	if(number.length()!=10) {
	    		Userform.recordError(numberfield, "Contact number should be contain 10 digits");
	    	}
	    	if(isValidUrl(url)) {
	    	java.sql.Date sqlDate = new java.sql.Date(bdate.getTime());
	    	UploadedFile uploadedFile = file;
			String filePath = saveFile(uploadedFile);
			if(filePath==null) {
				 Userform.recordError(filefield, "Invalid file extension it only accepts .pdf extension");
			}else {
			
				List<User> usersToInsert = new ArrayList<>(); 
                User newUser = new User();               
                newUser.setEmail(email);
                newUser.setUrl(url);
                newUser.setBdate(sqlDate);
                newUser.setCnumber(number);
                newUser.setFilepath(filePath);               
                usersToInsert.add(newUser);
                userService.updateUserByEmail(usersToInsert);				    
				 
				 return Login.class;	  
				}
	    	}
	    }
	    return null;
	}
	
	 public static boolean isValidUrl(String url) {
	        Pattern pattern = Pattern.compile(pat);
	        Matcher matcher = pattern.matcher(url);
	        return matcher.matches();
	  }
	    
	  private String saveFile(UploadedFile uploadedFile) {
	    	String targetDirectory = "C:/Users/ASUS/eclipse-workspace/Sampleproject/Files/";
	    	String targetFileName = uploadedFile.getFileName();
	    	String targetFilePath = targetDirectory + targetFileName;
	    	Path pdfFilePath = Path.of(targetFilePath);
 	        if (isPDF( pdfFilePath)) {
 	        	try {        
	    	        File targetFile = new File(targetFilePath);
	    	        File directory = new File(targetDirectory);
	    	        if (!directory.exists()) {
	    	            directory.mkdirs(); // Create any missing directories in the path
	    	        }
	    	        uploadedFile.write(targetFile);	    	        
	    	        return targetFilePath;
	    	       
	    	     } catch (Exception e) {
	    	        e.printStackTrace();
	    	        return null; 
	    	    }
 	       }else{
 	    	   System.out.println("file path eroor");
 	    	   Userform.recordError(filefield, "Invalid file extension");
	        	return null;
	       }
 	   	}
	    
	    public static boolean isPDF(Path filePath) {
	        String fileExtension = getFileExtension(filePath);
	        return fileExtension != null && fileExtension.equalsIgnoreCase("pdf");
	    }
	    
	    private static String getFileExtension(Path filePath) {
	        String fileName = filePath.getFileName().toString();
	        int lastDotIndex = fileName.lastIndexOf('.');
	        if (lastDotIndex > 0) {
	            return fileName.substring(lastDotIndex + 1).toLowerCase();
	        }
	        return null; 
	   }
}
